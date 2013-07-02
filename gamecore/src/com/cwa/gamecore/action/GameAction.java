package com.cwa.gamecore.action;

import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.message.GameResponse;
import com.cwa.gamecore.session.GameSession;

/**
 * GameAction实现了IAction接口。
 * 当以下两种情况满足时，应该选择继承GameAction而不是直接实现IAction接口：
 * 
 * 1）请求和响应分开定义
 * 2）不需要知道Session（或者根本不需要Session，比如SNS游戏）
 */
public abstract class GameAction<TReq extends GameRequest,
                        TResp extends GameResponse> implements IAction<TReq> {

    @Override
    public void execute(GameSession session, TReq req) {
        TResp resp = doExecute(req);
        session.write(resp);
    }
    
    /**
     * 给子类一个拦截execute()方法的机会。
     */
    protected TResp doExecute(TReq req) {
        return execute(req);
    }
    
    /**
     * 处理请求，返回响应。
     */
    public abstract TResp execute(TReq req);
    
}
