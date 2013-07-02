package com.cwa.gamecore.dispatcher;

import com.cwa.gamecore.action.ActionFactory;
import com.cwa.gamecore.action.IAction;
import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.session.GameSession;
import org.apache.log4j.Logger;

public class DefaultActionDispatcher implements ActionDispatcher {

    private static final Logger logger = Logger.getLogger(DefaultActionDispatcher.class);

    @Override
    public void dispatchAction(GameSession session, GameRequest message) {
        if (logger.isDebugEnabled()) {
            logger.debug("dispatch message:" + message);
        }
        
        int commandId = message.getCommandId();
        IAction action = ActionFactory.getInstance().getAction(commandId);
        if (action == null) {
            throw new RuntimeException("Invalid command ID: " + commandId);
        }
        
        if (logger.isDebugEnabled()) {
            logger.debug("execute action:" + action);
        }
        action.execute(session, message);
    }
    
}
