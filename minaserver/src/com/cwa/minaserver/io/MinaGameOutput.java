package com.cwa.minaserver.io;

import com.cwa.gamecore.io.AbstractGameOutput;
import com.cwa.gamecore.message.GameMessage;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * Mina版GameOutput。内部包装Mina的IoBuffer。
 */
public class MinaGameOutput extends AbstractGameOutput {
    
    private IoBuffer out;

    public MinaGameOutput(IoBuffer out) {
        this.out = out;
    }
    
    public IoBuffer getIoBuffer() {
        return out;
    }
    
    @Override
    public void put(byte b) {
        out.put(b);
    }
    
    @Override
    public void putShort(short s) {
        out.putShort(s);
    }
    
    @Override
    public void putInt(int i) {
        out.putInt(i);
    }

    @Override
    public void putLong(long l) {
        out.putLong(l);
    }
    
    @Override
    public void putBoolean(boolean b) {
        out.put(b ? (byte)1 : (byte)0);
    }
    
    @Override
    public void putString(String s) {
        if (s == null || s.length() == 0) {
            out.putShort((short) 0);
            return;
        }
        
        try {
            byte[] b = s.getBytes(GameMessage.DEFAULT_CHARSET);
            out.putShort((short) b.length);
            out.put(b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putBytes(byte[] bytes) {
        out.putShort((short) bytes.length);
        out.put(bytes);
    }

    @Override
    public void putRawBytes(byte[] bytes) {
        out.put(bytes);
    }
    
	@Override
	public void putChar(char arg0) {
		out.putChar(arg0);
	}

	@Override
	public void putDouble(double arg0) {
		out.putDouble(arg0);
	}

	@Override
	public void putFloat(float arg0) {
		out.putFloat(arg0);
	}
    
}
