/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package com.cwa.nettyserver.codec;

import com.cwa.gamecore.io.AbstractGameInput;
import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Netty的ChannelBuffer模拟的GameInput
 * @author zero
 */
public class NettyGameInput extends AbstractGameInput {
	private final ChannelBuffer buffer;
	
	public NettyGameInput(ChannelBuffer buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public byte get() {
		return buffer.readByte();
	}

	@Override
	public boolean getBoolean() {
		return (buffer.readByte() == 0) ? false : true;
	}

	@Override
	public byte[] getBytes() {
		byte[] content = new byte[buffer.readableBytes()];
		buffer.readBytes(content);
		return content;
	}

    @Override
    public byte[] getRawBytes(int n) {
		byte[] content = new byte[n];
		buffer.readBytes(content);
		return content;
    }

    @Override
    public char getChar() {
        return buffer.readChar();
    }

    @Override
    public double getDouble() {
        return buffer.readDouble();
    }

    @Override
    public float getFloat() {
        return buffer.readFloat();
    }

	@Override
	public int getInt() {
		return buffer.readInt();
	}

	@Override
	public long getLong() {
		return buffer.readLong();
	}

	@Override
	public short getShort() {
		return buffer.readShort();
	}

	@Override
	public String getString() {
		int len = getInt();
		byte[] bytes = new byte[len];
		buffer.readBytes(bytes);
		return new String(bytes);
	}

	@Override
	public int remaining() {
		return buffer.readableBytes();
	}

}
