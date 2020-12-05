package protocol.resultDomain;

import io.MysqlByteArrayInputStream;
import io.PacketChannel;
import protocol.command.AuthenticateCommand;
import protocol.command.Command;
import protocol.dataPackage.*;
import protocol.resultDomain.mode.CommandResult;
import protocol.resultDomain.mode.ErrorResult;
import protocol.resultDomain.mode.OkResult;
import protocol.resultDomain.mode.QueryResult;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


public class OperateService {
    /**
     * 退出命令
     * @param channel
     * @param command
     * @return
     * @throws IOException
     */
    public CommandResult solveQuit(PacketChannel channel, Command command) throws IOException {
        channel.write(command);
        int read = channel.getSocket().getInputStream().read();
        if (read == -1) {
            channel.close();
        }
        CommandResult commandResult = new CommandResult();
        commandResult.setType("this quit result: " + !channel.isOpen());
        return commandResult;
    }

    /**
     * 查询命令
     * @param channel
     * @param command
     * @return
     * @throws IOException
     */
    public CommandResult getQueryResult(PacketChannel channel, Command command) throws IOException {
        QueryResult queryResult = null;
        channel.write(command);
        // 列的数量
        byte[] lenth = channel.read();
        if (lenth[0] == (byte) 0xFF) { // 查询有错误
            return new ErrorResult(lenth);
        } else if (lenth[0] == (byte) 0x00) { // ok 包
            return new OkResult(lenth);
        } else { // 获取长度
            queryResult = new QueryResult();
            queryResult.setColumnNum(new MysqlByteArrayInputStream(lenth).readPackedInteger());
        }
        // 列的定义获取
        List<ColumnPackage> columnPackages = new LinkedList<>();
        for (byte[] bytes; (bytes = channel.read())[0] != (byte) 0xFE /* eof */; ) {
            columnPackages.add(new ColumnPackage(bytes));
        }
        queryResult.setColumns(columnPackages.toArray(new ColumnPackage[columnPackages.size()]));
        // 内容获取
        List<ResultSetRowPacket> resultSetRowPackets = new LinkedList<>();
        // 每次读取一行数据
        for (byte[] bytes; (bytes = channel.read())[0] != (byte) 0xFE /* eof */; ) {
            resultSetRowPackets.add(new ResultSetRowPacket(bytes));
        }
        queryResult.setRows(resultSetRowPackets.toArray(new ResultSetRowPacket[resultSetRowPackets.size()]));
        return queryResult;
    }

    /**
     * 登录
     * @param ip
     * @param port
     * @param user
     * @param pwd
     * @param database
     * @return
     * @throws IOException
     */
    public PacketChannel login(String ip, int port, String user, String pwd, String database) throws IOException {
        Socket socket = new Socket(ip, port);
        PacketChannel channel = new PacketChannel(socket);
        GreetingPacket greetingPacket = receiveGreeting(channel);
        // 认证
        AuthenticateCommand authenticateCommand = new AuthenticateCommand(database, user, pwd,
                greetingPacket.getScramble());
        authenticateCommand.setCollation(greetingPacket.getServerCollation());
        channel.write(authenticateCommand, 1);
        byte[] authenticationResult = channel.read();
        if (authenticationResult[0] != (byte) 0x00) { // error
            if (authenticationResult[0] == (byte) 0xFF) {
                ErrorPacket errorPacket = new ErrorPacket(authenticationResult);
                throw new RuntimeException(errorPacket.getErrorMessage() + errorPacket.getErrorCode()
                        + errorPacket.getSqlState());
            }
            // 未知错误
            throw new RuntimeException("Unexpected authentication result (" + authenticationResult[0] + ")");
        }
        return channel;
    }

    /**
     * 处理登录的包
     *
     * @param channel
     * @return
     * @throws IOException
     */
    private GreetingPacket receiveGreeting(PacketChannel channel) throws IOException {
        byte[] initialHandshakePacket = channel.read();
        if (initialHandshakePacket[0] == (byte) 0xFF /* error */) {
            ErrorPacket errorPacket = new ErrorPacket(initialHandshakePacket);
            throw new RuntimeException(errorPacket.getErrorMessage() + errorPacket.getErrorCode()
                    + errorPacket.getSqlState());
        }
        return new GreetingPacket(initialHandshakePacket);
    }
}
