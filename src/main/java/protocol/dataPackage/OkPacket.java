package protocol.dataPackage;

import io.MysqlByteArrayInputStream;

import java.io.IOException;

public class OkPacket {

    private int flag; // 标志
    private int effectRow; // 影响的行
    private int lastInsertID; // 最后插入的id

    public OkPacket(byte[] bytes) throws IOException {
        MysqlByteArrayInputStream buffer = new MysqlByteArrayInputStream(bytes);
        flag = buffer.readInteger(1);
        effectRow = buffer.readPackedInteger();
        lastInsertID = buffer.readPackedInteger();
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getEffectRow() {
        return effectRow;
    }

    public void setEffectRow(int effectRow) {
        this.effectRow = effectRow;
    }

    public int getLastInsertID() {
        return lastInsertID;
    }

    public void setLastInsertID(int lastInsertID) {
        this.lastInsertID = lastInsertID;
    }

    @Override
    public String toString() {
        return "OkPacket{" +
                "flag=" + flag +
                ", effectRow=" + effectRow +
                ", lastInsertID=" + lastInsertID +
                '}';
    }
}
