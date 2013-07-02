package com.cwa.client.message;

import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.message.IMessageFactory;
import java.util.HashMap;
import java.util.Map;

public class ClientMessageFactory implements IMessageFactory {
    
    // 单例
    private static final ClientMessageFactory INSTANCE = new ClientMessageFactory();
    public static ClientMessageFactory getInstance() {
        return INSTANCE;
    }
    
    
    private final Map<Integer, Class<? extends GameMessage>> msgClassMap;
    
    private ClientMessageFactory() {
        msgClassMap = new HashMap<Integer, Class<? extends GameMessage>>();
    }
    
    public void register(GameMessage msg) {
        msgClassMap.put(msg.getCommandId(), msg.getClass());
    }

    @Override
    public GameMessage getMessage(int cmdId) {
        Class<? extends GameMessage> msgClass = msgClassMap.get(cmdId);
        if (msgClass == null) {
            throw new RuntimeException("Unknown command ID: " + cmdId);
        }
        
        try {
            return msgClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can not instantiate " + msgClass, e);
        }
    }
    
}
