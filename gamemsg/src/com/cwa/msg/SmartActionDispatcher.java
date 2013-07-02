package com.cwa.msg;

import com.cwa.gamecore.dispatcher.DefaultActionDispatcher;
import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.message.GameResponse;
import com.cwa.gamecore.session.CatchResponseGameSession;
import com.cwa.gamecore.session.GameSession;
import com.cwa.msg.batch.BatchRequest;
import com.cwa.msg.batch.BatchResponse;
import com.cwa.msg.compression.GzipRequest;
import com.cwa.msg.encryption.CipherRequest;
import com.cwa.msg.mac.MacRequest;
import com.cwa.msg.sign.SignResponseGameSession;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

public class SmartActionDispatcher extends DefaultActionDispatcher {

    private static final Logger logger = Logger.getLogger(SmartActionDispatcher.class);
    
    private SecretProvider secretProvider;
    
    // 注意：safeCommands和unsafeCommands不应该同时配置
    private List<Integer> safeCommands;
    private List<Integer> unsafeCommands;
    
    // 需要RSA签名的响应消息
    private List<Integer> responsesNeedToSign;

    public SmartActionDispatcher() {
        
    }

    public void setSecretProvider(SecretProvider secretProvider) {
        this.secretProvider = secretProvider;
    }
    public SecretProvider getSecretProvider() {
        return secretProvider;
    }

    /*
     * 设置安全消息列表。
     * 如果配置了该列表的话，列表中的消息可以被安全执行。
     */
    public void setSafeCommands(List<Integer> safeCommands) {
        this.safeCommands = safeCommands;
    }
    public List<Integer> getSafeCommands() {
        return safeCommands;
    }

    /*
     * 不安全消息列表。
     * 如果配置了该列表的话，只有列表中的消息需要受到保护。
     */
    public void setUnsafeCommands(List<Integer> unsafeCommands) {
        this.unsafeCommands = unsafeCommands;
    }
    public List<Integer> getUnsafeCommands() {
        return unsafeCommands;
    }

    /**
     * 设置需要RSA签名的响应消息列表。
     */
    public void setResponsesNeedToSign(List<Integer> responsesNeedToSign) {
        this.responsesNeedToSign = responsesNeedToSign;
    }
    public List<Integer> getResponsesNeedToSign() {
        return responsesNeedToSign;
    }
    
    @Override
    public void dispatchAction(GameSession session, GameRequest req) {
        switch (req.getCommandId()) {
            case GameMessage.CMD_RSA_ACTION: {
                // 加密消息
                handleCipherReq(session, (CipherRequest) req);
                return;
            }
            case GameMessage.CMD_MD5_ACTION:
            case GameMessage.CMD_SHA256_ACTION: {
                // 消息带有MAC
                handleMacReq(session, (MacRequest) req);
                return;
            }
            case GameMessage.CMD_GZIP_ACTION: {
                // GZIP消息
                handleGzipReq(session, (GzipRequest) req);
                return;
            }
            case GameMessage.CMD_BATCH_ACTION: {
                // 批量消息
                handleBatchReq(session, (BatchRequest) req);
                return;
            }
        }
        
        if (isSafeMessage(req)) {
            // 安全消息
            handleSafeReq(session, req);
            return;
        }
        
        // 不允许直接执行的消息！
        throw new RuntimeException("Unsafe message: " + req);
    }
    
    private void handleCipherReq(GameSession session, CipherRequest req) {
        if (logger.isDebugEnabled()) {
            logger.debug("handle cipher message:" + req);
        }

        req.decrypt();
        execReq(session, req.getPlainRequest());
    }
    
    private void handleMacReq(GameSession session, MacRequest req) {
        if (logger.isDebugEnabled()) {
            logger.debug("handle message with MAC:" + req);
        }
        
        checkMac(session, req);
        execReq(session, req.getWrappedRequest());
    }
    
    // 检查MAC
    private void checkMac(GameSession session, MacRequest req) {
        byte[] secretData = secretProvider.getSecret(session, req.getWrappedRequest());
        if (!req.checkMac(secretData)) {
            // MAC不匹配
            if (logger.isDebugEnabled()) {
                logger.debug("MAC not match!");
                logger.debug("message    MAC:" + Arrays.toString(req.getMsgMac()));
                logger.debug("calculated MAC:" + Arrays.toString(req.getMsgMac2()));
            }
            throw new RuntimeException("Message has been modified!");
        }
    }
    
    private void handleGzipReq(GameSession session, GzipRequest req) {
        if (logger.isDebugEnabled()) {
            logger.debug("handle GZIP message:" + req);
        }
        
        dispatchAction(session, req.getWrappedRequest());
    }
    
    private void handleBatchReq(GameSession session, BatchRequest req) {
        if (logger.isDebugEnabled()) {
            logger.debug("handle batch message:" + req);
        }
        
        GameRequest[] subReqs = req.getRequests();
        GameResponse[] subResps = new GameResponse[subReqs.length];
        
        CatchResponseGameSession subSession = new CatchResponseGameSession();
        for (int i = 0; i < subReqs.length; i++) {
            GameRequest subReq = subReqs[i];
            dispatchAction(subSession, subReq);
            GameResponse subResp = subSession.getResponse();
            subResps[i] = subResp;
        }
        
        BatchResponse resp = new BatchResponse(subResps);
        session.write(resp);
    }
    
    // 是否消息可以被直接处理
    private boolean isSafeMessage(GameRequest req) {
        if (safeCommands != null && safeCommands.contains(req.getCommandId())) {
            return true;
        }
        
        if (unsafeCommands != null && !unsafeCommands.contains(req.getCommandId())) {
            return true;
        }
        
        return false;
    }
    
    // 安全消息，直接处理
    private void handleSafeReq(GameSession session, GameRequest req) {
        if (logger.isDebugEnabled()) {
            logger.debug("handle safe message:" + req);
        }
        
        execReq(session, req);
    }
    
    private void execReq(GameSession session, GameRequest req) {
        if (needToSignResponse(req)) {
            super.dispatchAction(new SignResponseGameSession(session), req);
        } else {
            super.dispatchAction(session, req);
        }
    }
    
    // 是否需要对响应消息RSA签名
    private boolean needToSignResponse(GameRequest req) {
        return responsesNeedToSign != null
                && responsesNeedToSign.contains(req.getCommandId());
    }
    
}
