package protocol.command;

import io.MysqlByteArrayOutputStream;

import java.io.IOException;

public class QuitCommand implements Command {
    @Override
    public byte[] toByteArray() throws IOException {
        MysqlByteArrayOutputStream buffer = new MysqlByteArrayOutputStream();
        buffer.writeInteger(CommandType.QUIT.ordinal(), 1);
        return buffer.toByteArray();
    }
}
