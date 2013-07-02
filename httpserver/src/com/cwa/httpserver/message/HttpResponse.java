package com.cwa.httpserver.message;

import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.AbstractResponse;

/**
 * HttpResponse包含一个响应码，表示响应是成功（0）还是失败（非0）状态。
 * HttpResponse的解码方法会先写short型响应码，如果响应码表示成功，则写后续数据。
 */
public abstract class HttpResponse extends AbstractResponse {
    
    // 0到9被预留，其他响应码由具体的游戏自己定义
    public static final int RESPONSE_CODE_OK = 0; // 成功
    public static final int RC_UNKNOWN_ERROR = 1; // 未知错误
    public static final int RC_NETWORK_ERROR = 2; // 网络异常
    public static final int RC_DB_ERROR      = 3; // 数据库异常
    public static final int RC_UNDEFINED4    = 4;
    public static final int RC_UNDEFINED5    = 5;
    public static final int RC_UNDEFINED6    = 6;
    public static final int RC_UNDEFINED7    = 7;
    public static final int RC_UNDEFINED8    = 8;
    public static final int RC_UNDEFINED9    = 9;
    
    
    // 响应码，0表示成功，非0表示失败
    private int responseCode;
    
    // 错误信息
    private String errorMsg;

    public HttpResponse(int cmdId) {
        super(cmdId);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public boolean isSuccessful() {
        return responseCode == RESPONSE_CODE_OK;
    }
    
    @Override
    public final void encodeBody(GameOutput out) {
        writeResponseCode(out);
        if (responseCode == RESPONSE_CODE_OK) {
            encode(out);
        } else {
            out.putString(errorMsg);
        }
    }
    
    /**
     * 写short型响应码。
     */
    public void writeResponseCode(GameOutput out) {
        out.putShort((short) responseCode);
    }
    
    /**
     * 写其余数据。这个方法由子类实现。
     */
    public abstract void encode(GameOutput out);
    
    @Override
    public String toString() {
        return getClass().getSimpleName() +  "{responseCode=" + responseCode + '}';
    }
    
}
