package com.cwa.protobuf;

import com.cwa.gamecore.session.GameSession;
import com.google.protobuf.GeneratedMessageLite;

public interface ProtobufAction<T extends GeneratedMessageLite> {
    
    public GeneratedMessageLite execute(GameSession session, T req);
    
}
