package com.cwa.gamecore.message;

public interface IMessageFactory {
    
    public GameRequest getMessage(int commandId);
    
}
