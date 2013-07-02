package com.cwa.protobuf;

import com.cwa.gamecore.action.IAction;
import com.cwa.gamecore.session.GameSession;
import com.google.gson.Gson;
import com.google.protobuf.GeneratedMessageLite;
import org.apache.log4j.Logger;

public class ProtobufGameAction implements IAction<ProtobufGameMessage> {

    private static Logger logger = Logger.getLogger(ProtobufGameAction.class);
    
    @Override
    public void execute(GameSession session, ProtobufGameMessage req) {
        GeneratedMessageLite protobufReq = req.getMessage();
        if (logger.isDebugEnabled()) {
            logger.debug("Protobuf request: " + toJSON(protobufReq));
        }
        
        ProtobufAction protobufAction = ProtobufSupport.getAction(req.msgId);
        GeneratedMessageLite protobufResp = protobufAction.execute(session, protobufReq);
        if (logger.isDebugEnabled()) {
            logger.debug("Protobuf response: " + toJSON(protobufResp));
        }
        
        ProtobufGameMessage resp = new ProtobufGameMessage();
        resp.setMessage(protobufResp);
        session.write(resp);
    }
    
    // TODO optimize
    private String toJSON(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
    
}
