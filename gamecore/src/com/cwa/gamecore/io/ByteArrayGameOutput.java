package com.cwa.gamecore.io;

import com.cwa.gamecore.message.GameMessage;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * GameOutput实现。内部使用ByteArrayOutputStream。
 */
public class ByteArrayGameOutput extends AbstractGameOutput {

    private FastByteArrayOutputStream baos;
    private DataOutputStream out;

    public ByteArrayGameOutput() {
        baos = new FastByteArrayOutputStream();
        out = new DataOutputStream(baos);
    }

    /**
     * 返回内部的字节数组。
     */
    public byte[] toByteArray() {
        return baos.toByteArray();
    }
    
    @Override
    public void putBoolean(boolean b) {
        try {
            out.writeBoolean(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void put(byte b) {
        try {
            out.writeByte(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putShort(short s) {
        try {
            out.writeShort(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putInt(int i) {
        try {
            out.writeInt(i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putLong(long l) {
        try {
            out.writeLong(l);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public void putChar(char value) {
        try {
            out.writeChar(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public void putFloat(float value) {
        try {
            out.writeFloat(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public void putDouble(double value) {
        try {
            out.writeDouble(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}

    @Override
    public void putRawBytes(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes could not be null!");
        }
        
        try {
            out.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putBytes(byte[] bytes) {
        try {
            if (bytes == null) {
                out.writeShort(0);
                return;
            }
            
            out.writeShort(bytes.length);
            out.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putString(String s) {
        try {
            if (s == null || s.isEmpty()) {
                out.writeShort(0);
                return;
            }
            
            byte[] bytes = s.getBytes(GameMessage.DEFAULT_CHARSET);
            out.writeShort(bytes.length);
            out.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
