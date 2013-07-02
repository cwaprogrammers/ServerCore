package com.cwa.httpserver.action;

import com.cwa.gamecore.GameException;
import com.cwa.gamecore.action.GameExecutor;
import com.cwa.httpserver.message.HttpRequest;
import com.cwa.httpserver.message.HttpResponse;
import com.cwa.httpserver.message.SimpleResponse;
import org.apache.log4j.Logger;

/**
 * 为采用HTTP协议的游戏服务器设计的Action超类。
 * 
 * @see HttpRequest
 * @see HttpResponse
 */
public abstract class HttpAction<TReq extends HttpRequest,
                                TResp extends HttpResponse> extends GameExecutor<TReq, HttpResponse> {

    private static final Logger logger = Logger.getLogger(HttpAction.class);
    
    /*
     * 处理请求消息之前，先检查请求消息。
     */
    public void checkRequest(TReq req) throws GameException {
        // 子类实现
    }
    
    /**
     * 统计Action执行时间、捕获异常。
     */
    @Override
    public HttpResponse doExecute(TReq req) {
        if (logger.isDebugEnabled()) {
            logger.debug("Request: " + req);
        }
        
        long startTime = System.currentTimeMillis();
        
        HttpResponse resp;
        try {
            checkRequest(req);
            resp = execute(req);
        } catch (GameException e) {
            logger.error("Game exception:", e);
            resp = new SimpleResponse(req.getCommandId());
            resp.setResponseCode(e.getErrorCode());
            resp.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("Unknow exception:", e);
            resp = new SimpleResponse(req.getCommandId());
            resp.setResponseCode(HttpResponse.RC_UNKNOWN_ERROR);
            resp.setErrorMsg(e.getMessage());
        }
        
        // 统计方法执行时间
        if (logger.isDebugEnabled()) {
            long endTime = System.currentTimeMillis();
            long time = (endTime - startTime);
            logger.debug("Time consumed: " + time + "ms");
            logger.debug("Response: " + resp);
        }
        
        return resp;
    }
    
}
