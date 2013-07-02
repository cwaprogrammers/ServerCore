/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package com.cwa.nettyserver.framework;

import com.cwa.gamecore.message.GameResponse;
import com.cwa.gamecore.session.GameSession;
import java.util.Set;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

/**
 * 通过Channel构建一个可以在游戏中使用的GameSession。
 * @author zero
 */
public class NettyGameSession implements GameSession {
	private static final Logger logger = Logger.getLogger(NettyGameSession.class);
	
    private Channel channel;
	
	public NettyGameSession(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void write(GameResponse arg0) {
		channel.write(arg0);
	}

    @Override
    public void close() {
        channel.close();
    }
	
	@Override
	public void broadcast(GameResponse arg0) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    @Override
    public Object setAttribute(Object key, Object val) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getAttribute(Object key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Object> getAttributeKeys() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
