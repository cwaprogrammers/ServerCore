/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package com.cwa.nettyserver.codec;

import com.cwa.gamecore.message.GameMessage;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.cwa.gamecore.message.GameResponse;
import com.cwa.nettyserver.framework.AbstractNettyEncoder;

/**
 * 游戏加码类
 * @author zero
 */
public class GameEncoder extends AbstractNettyEncoder {
	@Override
	public ChannelBuffer encode(GameResponse response) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(1024);
		NettyGameOutput output = new NettyGameOutput(buffer);
		output.putBytes(GameMessage.MAGIC_NUMBERS);
		output.putInt(0);
		output.putInt(response.getCommandId());
		response.encodeBody(output);
		output.putInt(output.getLength() - GameMessage.MAGIC_NUMBERS.length - 4,
				GameMessage.MAGIC_NUMBERS.length);
		return buffer;
	}
}
