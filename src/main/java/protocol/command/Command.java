package protocol.command;


import java.io.IOException;

public interface Command {
    byte[] toByteArray() throws IOException;
}
