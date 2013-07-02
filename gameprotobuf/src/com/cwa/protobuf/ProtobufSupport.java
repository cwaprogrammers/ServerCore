package com.cwa.protobuf;

import com.google.protobuf.MessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.util.HashMap;
import java.util.Map;

public abstract class ProtobufSupport {
    
    private static final Map<Integer, Parser> parsers = new HashMap<Integer, Parser>();
    private static final Map<Integer, ProtobufAction> actions = new HashMap<Integer, ProtobufAction>();
    
    public static void register(int msgId, Parser parser, ProtobufAction action) {
        parsers.put(msgId, parser);
        actions.put(msgId, action);
    }
    
    public static ProtobufAction getAction(int msgId) {
        ProtobufAction action = actions.get(msgId);
        if (action == null) {
            throw new ProtobufException("Unknown protobuf msg: " + msgId);
        }
        
        return action;
    }
    
    public static MessageLite parseRequest(int msgId, byte[] data) throws InvalidProtocolBufferException {
        Parser parser = parsers.get(msgId);
        if (parser == null) {
            throw new ProtobufException("Unknown protobuf msg: " + msgId);
        }
        
        return (MessageLite) parser.parseFrom(data);
    }
    
}
