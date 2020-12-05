package protocol.dataPackage;

import io.MysqlByteArrayInputStream;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ResultSetRowPacket {

    private String[] values;

    public ResultSetRowPacket(byte[] bytes) throws IOException {
        MysqlByteArrayInputStream buffer = new MysqlByteArrayInputStream(bytes);
        List<String> values = new LinkedList<String>();
        while (buffer.available() > 0) {
            values.add(buffer.readLengthEncodedString());
        }
        this.values = values.toArray(new String[values.size()]);
    }

    public String[] getValues() {
        return values;
    }

    public String getValue(int index) {
        return values[index];
    }

    public int size() {
        return values.length;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "ResultSetRowPacket{" +
                "values=" + Arrays.toString(values) +
                '}';
    }
}
