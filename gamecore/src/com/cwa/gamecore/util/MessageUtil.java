package com.cwa.gamecore.util;

import com.cwa.gamecore.io.ByteArrayGameInput;
import com.cwa.gamecore.io.ByteArrayGameOutput;
import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.message.*;

/**
 * 消息相关工具类。
 */
public class MessageUtil {
    
    /**
     * 解码请求消息。
     */
    public static GameRequest decode(byte[] data) {
        GameInput in = new ByteArrayGameInput(data);
        return decode(in, MessageFactory.getInstance());
    }
    
    /**
     * 解码请求消息。
     */
    public static GameRequest decode(GameInput in, IMessageFactory msgFactory) {
        if (in.remaining() < 6) {
            throw new RuntimeException("No enough data, available bytes: " + in.remaining());
        }
        
        readMagicNumbers(in);
        checkMessageLength(in);
        
        int cmdId = in.getShort();
        GameRequest req = msgFactory.getMessage(cmdId);
        if (req == null) {
            throw new RuntimeException("Unknown command ID:" + cmdId);
        }
        
        req.decodeBody(in);
        if (in.remaining() != 0) {
            throw new RuntimeException("Error decoding " + req.getClass().getSimpleName() + 
                    ", superfluous bytes: " + in.remaining());
        }
        
        return req;
    }
    
    private static void readMagicNumbers(GameInput in) {
        for (int i = 0; i < GameMessage.MAGIC_NUMBERS.length; i++) {
            byte magicNumber = in.get();
            if (magicNumber != GameMessage.MAGIC_NUMBERS[i]) {
                throw new RuntimeException("Bad message magic number " + i + ":" + magicNumber);
            }
        }
    }
    
    private static void checkMessageLength(GameInput in) {
        //int length = UShort.get(in);
        short length = in.getShort();
        if (in.remaining() != length) {
            throw new RuntimeException("Wrong message length:" + length + " available:" + in.remaining());
        }
    }
    
    /**
     * 编码响应消息。
     */
    public static byte[] encode(GameResponse resp) {
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        
        for (int i = 0; i < GameMessage.MAGIC_NUMBERS.length; i++) {
            out.put(GameMessage.MAGIC_NUMBERS[i]);
        }
        
        out.putShort((short) 0); //length
        out.putShort((short) resp.getCommandId());
        
        resp.encodeBody(out);
        
        byte[] bytes = out.toByteArray();
        fixMessageLength(bytes);
        
        return bytes;
    }
    
    private static void fixMessageLength(byte[] bytes) {
        //int length = bytes.length - GameMessage.MAGIC_NUMBERS.length - 2;
        int n = bytes.length - 4;
        bytes[2] = (byte) ((n >>> 8) & 0xFF);
        bytes[3] = (byte) ((n      ) & 0xFF);
    }
    
}
