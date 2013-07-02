/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package com.cwa.nettyserver.framework;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.cwa.gamecore.message.GameResponse;

/**
 * 编码器
 * @author zero
 */
public abstract class AbstractNettyEncoder extends SimpleChannelHandler {
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) 
    		throws Exception {
    	// it's the response message
    	if (e.getMessage() instanceof GameResponse) {
    		e.getChannel().write(encode((GameResponse) e.getMessage()));
    	} else {
    		// already encoded.
    		ctx.sendDownstream(e);
    	}
    }
    
    public abstract ChannelBuffer encode(GameResponse request); 
}
