package com.cwa.gamecore.message;

/**
 * 抽象的响应消息。子类需要实现消息的编码方法。
 */
public abstract class AbstractResponse implements GameResponse {
    
    private int commandId;

    public AbstractResponse(int commandId) {
        this.commandId = commandId;
    }

    @Override
    public int getCommandId() {
        return commandId;
    }
    
}
