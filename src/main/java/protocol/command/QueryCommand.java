package protocol.command;

import io.MysqlByteArrayOutputStream;

import java.io.IOException;

public class QueryCommand implements Command {

    private String sql;

    public QueryCommand(String sql) {
        this.sql = sql;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        MysqlByteArrayOutputStream buffer = new MysqlByteArrayOutputStream();
        buffer.writeInteger(CommandType.QUERY.ordinal(), 1);
        buffer.writeString(this.sql);
        return buffer.toByteArray();
    }

}
