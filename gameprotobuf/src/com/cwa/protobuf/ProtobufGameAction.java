package com.cwa.protobuf;

import com.cwa.gamecore.action.IAction;
import com.cwa.gamecore.session.GameSession;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.MessageLite;
import org.apache.log4j.Logger;

/**
 * IAction实现。将逻辑转发给ProtobufAction。
 */
public class ProtobufGameAction implements IAction<ProtobufGameMessage> {

    private static Logger logger = Logger.getLogger(ProtobufGameAction.class);
    
    @Override
    public void execute(GameSession session, ProtobufGameMessage req) {
        MessageLite pbReq = req.getMessage();
        if (logger.isDebugEnabled()) {
            logger.debug("Protobuf request: ");
            logger.debug(toJSON(pbReq));
        }
        
        ProtobufGameSession pbSession = new ProtobufGameSession(req.msgId, session);
        if (logger.isDebugEnabled()) {
            logger.debug("Role ID: ##### " + pbSession.safeGetRoleId() + " #####");
        }
        
        ProtobufAction pbAction = ProtobufSupport.getAction(req.msgId);
        MessageLite pbResp = pbAction.execute(pbSession, pbReq);
        if (logger.isDebugEnabled()) {
            logger.debug("Protobuf response: ");
            logger.debug(toJSON(pbResp));
        }
        
        ProtobufGameMessage resp = new ProtobufGameMessage();
        resp.setMessage(pbResp);
        session.write(resp);
    }
    
    // TODO optimize
    private String toJSON(Object obj) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(MyExclusionStrategy)
                .setPrettyPrinting()
                .create();
        
        return gson.toJson(obj);
    }
    
    private static ExclusionStrategy MyExclusionStrategy = new ExclusionStrategy() {

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            if (! f.getName().endsWith("_")) {
                return true;
            }
            if (f.getName().startsWith("bitField")) {
                return true;
            }
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
        
    };
    
}
