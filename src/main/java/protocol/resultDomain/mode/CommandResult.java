package protocol.resultDomain.mode;

public class CommandResult {
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CommandResult{" +
                "type='" + type + '\'' +
                '}';
    }
}
