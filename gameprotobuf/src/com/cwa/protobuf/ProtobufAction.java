package com.cwa.protobuf;

import com.google.protobuf.MessageLite;

public interface ProtobufAction<T extends MessageLite> {
    
    public MessageLite execute(ProtobufGameSession session, T req);
    
}
