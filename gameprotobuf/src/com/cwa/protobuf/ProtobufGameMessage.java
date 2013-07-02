package com.cwa.protobuf;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.util.StringUtil;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.MessageLite;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * GameMessage实现。内部封装了MessageLite。
 */
public class ProtobufGameMessage implements GameMessage {

    private static final Logger logger = Logger.getLogger(ProtobufGameMessage.class);
    private static final int PROTOBUF_MSG_ID = 100;
    
    int msgId;
    private MessageLite message;
    
    @Override
    public int getCommandId() {
        return PROTOBUF_MSG_ID;
    }

    public MessageLite getMessage() {
        return message;
    }

    public void setMessage(MessageLite message) {
        this.message = message;
    }

    @Override
    public void decodeBody(GameInput in) {
        byte[] data = in.getBytes();
        if (logger.isDebugEnabled()) {
            logger.debug("Decode protobuf request:");
            logger.debug(StringUtil.toDebugString(data));
        }
        
        try {
            // get msg id
            msgId = readMsgId(data);
            
            // parse msg
            message = ProtobufSupport.parseRequest(msgId, data);
        } catch (IOException e) {
            throw new ProtobufException("Failed to decode request!", e);
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
            
            byte[] data = os.toByteArray();
            out.putBytes(data);
            
            if (logger.isDebugEnabled()) {
                logger.debug("Encode protobuf response:");
                logger.debug(StringUtil.toDebugString(data));
            }
        } catch (IOException e) {
            throw new ProtobufException("Failed to encode response!", e);
        }
    }

    @Override
    public String toString() {
        return "{ProtobufGameMessage"
                + " msgId=" + msgId
                + " proto=" + (message == null ? null : message.getClass().getSimpleName())
                + "}";
    }
    
    // 工厂方法
    public static ProtobufGameMessage wrap(MessageLite msg) {
        ProtobufGameMessage wrapper = new ProtobufGameMessage();
        wrapper.setMessage(msg);
        return wrapper;
    }
    
}
