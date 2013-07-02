package com.cwa.gamecore.config;

import com.cwa.gamecore.dispatcher.ActionDispatcher;
import com.cwa.gamecore.dispatcher.DefaultActionDispatcher;
import java.util.Arrays;
import java.util.List;

/**
 * 通用的一些服务器配置。
 */
public class GameConfig {
    
    // 服务器监听端口
    private int serverPort = 11223;
    
    private List<String> messageSuffixes = Arrays.asList("Message");
    private List<String> messagePackages = Arrays.asList("com.cwa");
    
    private List<String> actionSuffixes = Arrays.asList("Action");
    private List<String> actionPackages = Arrays.asList("com.cwa");
    
    private ActionDispatcher actionDispatcher = new DefaultActionDispatcher();
    
    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public List<String> getActionSuffixes() {
        return actionSuffixes;
    }

    public void setActionSuffixes(List<String> actionSuffixes) {
        this.actionSuffixes = actionSuffixes;
    }

    public List<String> getActionPackages() {
        return actionPackages;
    }

    public void setActionPackages(List<String> actionPackages) {
        this.actionPackages = actionPackages;
    }

    public List<String> getMessageSuffixes() {
        return messageSuffixes;
    }

    public void setMessageSuffixes(List<String> messageSuffixes) {
        this.messageSuffixes = messageSuffixes;
    }

    public List<String> getMessagePackages() {
        return messagePackages;
    }

    public void setMessagePackages(List<String> messagePackages) {
        this.messagePackages = messagePackages;
    }
    
    public ActionDispatcher getActionDispatcher() {
        return actionDispatcher;
    }

    public void setActionDispatcher(ActionDispatcher actionDispatcher) {
        this.actionDispatcher = actionDispatcher;
    }
    
}
