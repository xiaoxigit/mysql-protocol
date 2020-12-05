package protocol.resultDomain.mode;

import protocol.dataPackage.OkPacket;

import java.io.IOException;

public class OkResult extends CommandResult {

    private OkPacket okPacket;

    public OkResult(byte[] bytes) throws IOException {
        type = "ok result";
        okPacket = new OkPacket(bytes);
    }

    public OkPacket getOkPacket() {
        return okPacket;
    }

    public void setOkPacket(OkPacket okPacket) {
        this.okPacket = okPacket;
    }

    @Override
    public String toString() {
        return "OkResult{" +
                "okPacket=" + okPacket +
                ", type='" + type + '\'' +
                '}';
    }
}
