package com.cwa.gamecore.message;

/**
 * 抽象的请求消息。子类需要实现消息的解码方法。
 */
public abstract class AbstractRequest implements GameRequest {
    
    private int commandId;

    public AbstractRequest(int commandId) {
        this.commandId = commandId;
    }

    @Override
    public int getCommandId() {
        return commandId;
    }
    
}
