package com.cwa.gamecore.dispatcher;

import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.session.GameSession;

/**
 * ActionDispatcher根据GameRequest实例化相应的IAction并且
 * 执行IAction的execute()方法。
 */
public interface ActionDispatcher {
    
    public void dispatchAction(GameSession session, GameRequest message);
    
}
