package com.cwa.gamecore.io;

import com.cwa.gamecore.message.GameMessage;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * GameInput实现。内部使用ByteArrayInputStream。
 */
public class ByteArrayGameInput extends AbstractGameInput {

    private DataInputStream in;

    public ByteArrayGameInput(byte[] data) {
        in = new DataInputStream(new FastByteArrayInputStream(data));
    }

    @Override
    public int remaining() {
        try {
            return in.available();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public boolean getBoolean() {
        try {
            return in.readBoolean();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte get() {
        try {
            return in.readByte();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public short getShort() {
        try {
            return in.readShort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInt() {
        try {
            return in.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getLong() {
        try {
            return in.readLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public char getChar() {
        try {
            return in.readChar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float getFloat() {
        try {
            return in.readFloat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getDouble() {
        try {
            return in.readDouble();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] getRawBytes(int n) {
        try {
            return readBytes(n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] getBytes() {
        try {
            short length = in.readShort();
            return readBytes(length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getString() {
        try {
            short length = in.readShort();
            if (length == 0) {
                return "";
            }
            
            byte[] bytes = readBytes(length);
            return new String(bytes, GameMessage.DEFAULT_CHARSET);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private byte[] readBytes(int n) throws IOException {
        if (n == 0) {
            return new byte[0];
        }
        
        byte[] bytes = new byte[n];
        
        int numRead = in.read(bytes);
        if (numRead != n) {
            String errMsg = String.format("Not enough data! Expected: %d, Remaining: %d", n, numRead);
            throw new RuntimeException(errMsg);
        }
        
        return bytes;
    }
    
}
