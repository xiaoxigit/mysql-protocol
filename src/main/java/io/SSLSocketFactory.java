package io;

import javax.net.ssl.SSLSocket;
import java.net.Socket;
import java.net.SocketException;

public interface SSLSocketFactory {

    SSLSocket createSocket(Socket socket) throws SocketException;
}
