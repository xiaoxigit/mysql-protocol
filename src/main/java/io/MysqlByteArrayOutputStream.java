package io;

import java.io.IOException;
import java.io.OutputStream;


public class MysqlByteArrayOutputStream extends OutputStream {

    private OutputStream outputStream;

    public MysqlByteArrayOutputStream() {
        this(new java.io.ByteArrayOutputStream());
    }

    public MysqlByteArrayOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Write int in little-endian format.
     */
    public void writeInteger(int value, int length) throws IOException {
        for (int i = 0; i < length; i++) {
            write(0x000000FF & (value >>> (i << 3)));
        }
    }

    /**
     * Write long in little-endian format.
     */
    public void writeLong(long value, int length) throws IOException {
        for (int i = 0; i < length; i++) {
            write((int) (0x00000000000000FF & (value >>> (i << 3))));
        }
    }

    public void writeString(String value) throws IOException {
        write(value.getBytes());
    }

    /**
     * @see MysqlByteArrayInputStream#readZeroTerminatedString()
     */
    public void writeZeroTerminatedString(String value) throws IOException {
        write(value.getBytes());
        write(0);
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    public byte[] toByteArray() {
        // todo: whole approach feels wrong
        if (outputStream instanceof java.io.ByteArrayOutputStream) {
            return ((java.io.ByteArrayOutputStream) outputStream).toByteArray();
        }
        return new byte[0];
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

}

