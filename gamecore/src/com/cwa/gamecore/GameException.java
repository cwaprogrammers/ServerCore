package com.cwa.gamecore;

/**
 * 游戏内异常。
 * 游戏内异常可能会和一个错误码（非0）关联。
 */
public class GameException extends RuntimeException {

    // 错误码必须大于0
    private int errorCode = 1;
    
    public GameException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public GameException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    
}
