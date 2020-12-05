package protocol.resultDomain.mode;

import protocol.dataPackage.ErrorPacket;

import java.io.IOException;

public class ErrorResult extends CommandResult {

    private ErrorPacket errorPacket;

    public ErrorResult(byte[] bytes) throws IOException {
        type = "error result";
       errorPacket = new ErrorPacket(bytes);
    }

    public ErrorPacket getErrorPacket() {
        return errorPacket;
    }

    public void setErrorPacket(ErrorPacket errorPacket) {
        this.errorPacket = errorPacket;
    }

    @Override
    public String toString() {
        return "ErrorResult{" +
                "errorPacket=" + errorPacket +
                ", type='" + type + '\'' +
                '}';
    }
}
