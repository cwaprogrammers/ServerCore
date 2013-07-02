/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package com.cwa.nettyserver.framework;

import java.net.SocketAddress;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * 解码器
 * @author zero
 */
public abstract class AbstractNettyDecoder extends SimpleChannelHandler {
	/**
	 * 一个假的，可以传递消息的东西。
	 */
	private static class MockMessageEvent implements MessageEvent {
		MessageEvent e;
		Object content;
		private MockMessageEvent(MessageEvent e, Object o) {
			this.e = e;
			this.content = o;
		}

		@Override
		public Channel getChannel() {
			return e.getChannel();
		}

		@Override
		public ChannelFuture getFuture() {
			return e.getFuture();
		}

		@Override
		public Object getMessage() {
			return content;
		}

		@Override
		public SocketAddress getRemoteAddress() {
			return e.getRemoteAddress();
		}
	}
	
    public void messageReceived(
            ChannelHandlerContext ctx, final MessageEvent e) throws Exception {
    	if (e.getMessage() instanceof ChannelBuffer) {
    		final Object o = decode((ChannelBuffer) e.getMessage());
    		ctx.sendUpstream(new MockMessageEvent(e, o));
    	} else {
    		ctx.sendUpstream(e); 
    	}
    }
    
    public abstract Object decode(ChannelBuffer content); 
}
