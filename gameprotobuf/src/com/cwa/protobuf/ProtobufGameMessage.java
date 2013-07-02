package com.cwa.protobuf;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.GameMessage;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.GeneratedMessageLite;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProtobufGameMessage implements GameMessage {

    private static final int PROTOBUF_MSG_ID = 100;
    
    int msgId;
    private GeneratedMessageLite message;
    
    @Override
    public int getCommandId() {
        return PROTOBUF_MSG_ID;
    }

    public GeneratedMessageLite getMessage() {
        return message;
    }

    public void setMessage(GeneratedMessageLite message) {
        this.message = message;
    }

    @Override
    public void decodeBody(GameInput in) {
        byte[] data = in.getBytes();
        try {
            // get msg id
            msgId = readMsgId(data);
            
            // parse msg
            message = ProtobufSupport.parseRequest(msgId, data);
        } catch (IOException e) {
            throw new RuntimeException("fail to decode request", e);
        }
    }
    
    private int readMsgId(byte[] msgData) throws IOException {
        CodedInputStream tmp = CodedInputStream.newInstance(msgData);
        tmp.readTag();
        return tmp.readInt32();
    }

    @Override
    public void encodeBody(GameOutput out) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            message.writeTo(os);
            out.putBytes(os.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to encode response", e);
        }
    }

    @Override
    public String toString() {
        return "{ProtobufGameMessage"
                + " msgId=" + msgId
                + " proto=" + message.getClass().getSimpleName()
                + "}";
    }
    
}
