package client;


import io.*;
import protocol.command.AuthenticateCommand;
import protocol.command.QueryCommand;
import protocol.command.QuitCommand;
import protocol.dataPackage.*;
import protocol.resultDomain.OperateService;
import protocol.resultDomain.mode.CommandResult;


public class Test {
    public static final String ADDRESS = "10.37.153.2";
    public static final int PORT = 3306;
    public static final String USER = "fabu";
    public static final String PWD = "SuningRds@123";

    public static void main(String[] args) throws Exception {
        CommandResult result;
        OperateService operateService = new OperateService();
        PacketChannel channel = operateService.login(ADDRESS, PORT, USER, PWD, null);
        //
        result = operateService.getQueryResult(channel, new QueryCommand("use rdrs2"));
        System.out.println(result.toString());
        //
        result = operateService.getQueryResult(channel, new QueryCommand("insert into ucartest values('7','3')"));
        System.out.println(result.toString());
        //
        result = operateService.getQueryResult(channel, new QueryCommand("show tables"));
        System.out.println(result.toString());
        //
        result = operateService.solveQuit(channel, new QuitCommand());
        System.out.println(result.toString());

    }


}
