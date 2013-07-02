/**
 * 2011-12-28 上午09:46:39
 */
package com.cwa.minaserver.session;

import com.cwa.gamecore.message.GameResponse;
import com.cwa.gamecore.session.GameSession;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.session.IoSession;

/**
 * @author zpc
 * @descirption 服务器输出句柄，专门用于发送消息
 */
public class MinaGameSession implements GameSession {
    
    private IoSession session;

    public MinaGameSession(IoSession session) {
        this.session = session;
    }

    public IoSession getSession() {
        return this.session;
    }

    @Override
    public void write(GameResponse message) {
        session.write(message);
    }

    @Override
    public void broadcast(GameResponse message) {
        Map<Long, IoSession> sessionMap = session.getService().getManagedSessions();
        Collection<IoSession> sessionCol = sessionMap.values();
        for (IoSession s : sessionCol) {
            s.write(message);
        }
    }

    @Override
    public Object setAttribute(Object key, Object val) {
        return session.setAttribute(key, val);
    }

    @Override
    public Object getAttribute(Object key) {
        return session.getAttribute(key);
    }

    @Override
    public Set<Object> getAttributeKeys() {
        return session.getAttributeKeys();
    }
        
	/**
	 * 
	 * @param immediately
	 *            是否立刻关闭
	 * @return
	 * @description 关闭会话
	 */
	public CloseFuture close(boolean immediately) {
		return session.close(immediately);
	}

	@Override
	public String toString() {
		return session.toString();
	}

	@Override
	public int hashCode() {
		return session.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MinaGameSession))
			return false;

		MinaGameSession targetObj = (MinaGameSession) obj;
		return session.equals(targetObj.session);
	}

	@Override
	public void close() {
		close(true);
	}
}
