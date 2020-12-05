package io;

import protocol.command.Command;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.Channel;


public class PacketChannel implements Channel {

    private Socket socket;
    private MysqlByteArrayInputStream inputStream;
    private MysqlByteArrayOutputStream outputStream;

    public PacketChannel(String hostname, int port) throws IOException {
        this(new Socket(hostname, port));
    }

    public PacketChannel(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = new MysqlByteArrayInputStream(socket.getInputStream());
        this.outputStream = new MysqlByteArrayOutputStream(socket.getOutputStream());
    }

    public MysqlByteArrayInputStream getInputStream() {
        return inputStream;
    }

    public MysqlByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public byte[] read() throws IOException {
        int length = inputStream.readInteger(3);
        inputStream.skip(1); //sequence
        return inputStream.read(length);
    }

    public void write(Command command, int packetNumber) throws IOException {
        byte[] body = command.toByteArray();
        outputStream.writeInteger(body.length, 3); // packet length
        outputStream.writeInteger(packetNumber, 1); // sequence
        outputStream.write(body, 0, body.length);
        // though it has no effect in case of default (underlying) output stream (SocketOutputStream),
        // it may be necessary in case of non-default one
        outputStream.flush();
    }

    public void write(Command command) throws IOException {
        write(command, 0);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void upgradeToSSL(SSLSocketFactory sslSocketFactory, HostnameVerifier hostnameVerifier) throws IOException {
        SSLSocket sslSocket = sslSocketFactory.createSocket(this.socket);
        sslSocket.startHandshake();
        socket = sslSocket;
        inputStream = new MysqlByteArrayInputStream(sslSocket.getInputStream());
        outputStream = new MysqlByteArrayOutputStream(sslSocket.getOutputStream());
        if (hostnameVerifier != null && !hostnameVerifier.verify(sslSocket.getInetAddress().getHostName(),
            sslSocket.getSession())) {
            throw new RuntimeException(sslSocket.getInetAddress().getHostName() +
                " identity was not confirmed");
        }
    }

    @Override
    public boolean isOpen() {
        return !socket.isClosed();
    }

    @Override
    public void close() throws IOException {
        try {
            socket.shutdownInput(); // for socketInputStream.setEOF(true)
        } catch (Exception e) {
            // ignore
        }
        try {
            socket.shutdownOutput();
        } catch (Exception e) {
            // ignore
        }
        socket.close();
    }
}
