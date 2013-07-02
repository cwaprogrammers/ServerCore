package com.cwa.gamecore.message;

/**
 * 抽象的游戏消息。子类需要实现消息的编码、解码方法。
 */
public abstract class AbstractMessage extends AbstractRequest implements GameMessage {
    
    public AbstractMessage(int cmdId) {
        super(cmdId);
    }

}
