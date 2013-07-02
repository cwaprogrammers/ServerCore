/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package com.cwa.nettyserver.codec;

import com.cwa.gamecore.io.AbstractGameOutput;
import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Netty针对GameOutput的实现。
 * @author zero
 */
public class NettyGameOutput extends AbstractGameOutput {
	public ChannelBuffer buffer;
	public NettyGameOutput(ChannelBuffer buffer) {
		this.buffer = buffer;
	}
	
	public void putInt(int b, int index) {
		int pos = buffer.writerIndex();
		buffer.writerIndex(index);
		putInt(b);
		buffer.writerIndex(pos);
	}
	
	@Override
	public void put(byte arg0) {
		buffer.writeByte(arg0);
	}

	@Override
	public void putBoolean(boolean arg0) {
		buffer.writeByte((arg0) ? 1 : 0);
	}

	@Override
	public void putBytes(byte[] arg0) {
		buffer.writeBytes(arg0);
	}

    @Override
    public void putRawBytes(byte[] bytes) {
        buffer.writeBytes(bytes);
    }

    @Override
    public void putChar(char c) {
        buffer.writeChar(c);
    }

    @Override
    public void putDouble(double d) {
        buffer.writeDouble(d);
    }

    @Override
    public void putFloat(float f) {
        buffer.writeFloat(f);
    }

	@Override
	public void putInt(int arg0) {
		buffer.writeInt(arg0);
	}

	@Override
	public void putLong(long arg0) {
		buffer.writeLong(arg0);
	}

	@Override
	public void putShort(short arg0) {
		buffer.writeShort(arg0);
	}

	@Override
	public void putString(String arg0) {
		putInt(arg0.length());
		buffer.writeBytes(arg0.getBytes());
	}
	
	public int getLength() {
		return buffer.writerIndex();
	}
}
