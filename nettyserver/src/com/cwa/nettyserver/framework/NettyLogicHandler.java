/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package com.cwa.nettyserver.framework;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.cwa.gamecore.action.ActionFactory;
import com.cwa.gamecore.action.IAction;
import com.cwa.gamecore.message.GameRequest;

/**
 * netty的逻辑分发机制
 * @author zero
 */
public class NettyLogicHandler extends SimpleChannelHandler {
    @SuppressWarnings("unchecked")
	public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    	if (e.getMessage() instanceof GameRequest) {
    		GameRequest request = (GameRequest) e.getMessage();
    		int commandId = request.getCommandId();
    		IAction<GameRequest> action = 
    				ActionFactory.getInstance().getAction(commandId);
    		action.execute(new NettyGameSession(e.getChannel()), request);
    	}
        ctx.sendUpstream(e);
    }

}
