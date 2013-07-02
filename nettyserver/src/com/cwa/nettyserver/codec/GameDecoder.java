/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package com.cwa.nettyserver.codec;

import com.cwa.gamecore.message.GameMessage;
import org.jboss.netty.buffer.ChannelBuffer;

import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.message.MessageFactory;
import com.cwa.nettyserver.framework.AbstractNettyDecoder;

/**
 * 游戏中实际使用的解码器
 * @author zero
 */
public class GameDecoder extends AbstractNettyDecoder {
	public static final byte[] HEAD = GameMessage.MAGIC_NUMBERS;
	@Override
	public Object decode(ChannelBuffer buffer) {
		// check length of buffer
		if (buffer.readableBytes() < 6) {
			return null;
		}
		
		buffer.markReaderIndex();
		// check head
		if (!checkHead(buffer, HEAD)) {
			buffer.resetReaderIndex();
			return null;
		}
		
		// decode content
		// get length
		int len = buffer.readInt();
		if (len > buffer.readableBytes()) {
			return null;
		}
		
		int commandId = buffer.readInt();
		// decode body
		GameRequest request = MessageFactory.getInstance().getMessage(commandId);
		if (null != request) 
			request.decodeBody(new NettyGameInput(buffer));
		return request;
	}
	
	private boolean checkHead(ChannelBuffer buffer, byte[] head) {
		byte[] content = new byte[2];
		buffer.readBytes(content);
		if (content[0] == head[0] && content[1] == head[1]) {
			return true;
		}
		return false;
	}

}
