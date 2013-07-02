package com.cwa.log.manage;

import com.cwa.gamecore.context.AppContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author Pai
 * 
 * 12.12.24
 */
public class GameContext {
    
    // 单例
    private static GameContext instance = new GameContext();
    public static GameContext getInstance() {
        return instance;
    }
    
    
    private ApplicationContext appCtx;
    
    private GameContext() {
//        appCtx = AppContext.getInstance().getApplicationContext();
        appCtx = new ClassPathXmlApplicationContext(new String[] {"config/root.xml"});
    }
    
    public ApplicationContext getAppContext() {
        return appCtx;
    }
}
