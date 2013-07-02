package com.cwa.minaserver.io;

import com.cwa.gamecore.io.AbstractGameInput;
import com.cwa.gamecore.message.GameMessage;
import java.nio.charset.CharacterCodingException;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * Mina版GameInput。内部包装Mina的IoBuffer。
 */
public class MinaGameInput extends AbstractGameInput {
    
    private IoBuffer in;

    public MinaGameInput(IoBuffer in) {
        this.in = in;
    }
    
    public IoBuffer getIoBuffer() {
        return in;
    }

    @Override
    public int remaining() {
        return in.remaining();
    }
    
    @Override
    public byte get() {
        return in.get();
    }
    
    @Override
    public short getShort() {
        return in.getShort();
    }
    
    @Override
    public int getInt() {
        return in.getInt();
    }

    @Override
    public long getLong() {
        return in.getLong();
    }
    
    @Override
    public boolean getBoolean() {
        return (in.get() == 0 ? false : true);
    }
    
    @Override
    public String getString() {
        short length = in.getShort();
        try {
            return in.getString(length, GameMessage.DEFAULT_CHARSET.newDecoder());
        } catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] getBytes() {
        short length = in.getShort();
        byte[] bytes = new byte[length];
        in.get(bytes);

        return bytes;
    }

    @Override
    public byte[] getRawBytes(int n) {
        byte[] bytes = new byte[n];
        in.get(bytes);
        return bytes;
    }

	@Override
	public char getChar() {
		return in.getChar();
	}

	@Override
	public double getDouble() {
		return in.getDouble();
	}

	@Override
	public float getFloat() {
		return in.getFloat();
	}
    
}
