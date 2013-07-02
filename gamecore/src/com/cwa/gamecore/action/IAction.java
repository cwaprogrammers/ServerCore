package com.cwa.gamecore.action;

import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.session.GameSession;

/**
 * IAction接口主要是给纯联网游戏（MMORPG、棋牌游戏等）提供的。
 * 具体的游戏服务器通过实现这个接口来实现游戏逻辑处理。
 */
public interface IAction<T extends GameRequest> {

    /**
     * 处理游戏请求。
     * 
     * 注意：子类实现这个方法时，必须自己调用session.write(msg)方法将响应消息
     *      发回给客户端。
     */
    public void execute(GameSession session, T message);
    
}
