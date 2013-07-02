package com.cwa.minaserver;

import com.cwa.gamecore.action.ActionFactory;
import com.cwa.gamecore.message.MessageFactory;
import com.cwa.minaserver.codec.DefaultProtocolCodecFactory;
import com.cwa.minaserver.config.GameServerConfig;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFactory;

public class GameMsgServer {

    private static final Logger logger = Logger.getLogger(GameMsgServer.class);
    private int serverPort;
    private TCPServer tcpServer;

    /**
     * 采用默认DefaultSessionHandler进行初始化
     * @param config
     */
    public GameMsgServer(GameServerConfig config) {
    	createPort(config);
        
        createProtocolCodecFactory(config);
        
        createSessionHandler(config, null);

        createFactory(config);
    }
    
    /**
     * 采用开发者自行定制的sessionHandler进行初始化，sessionHandler必须继承DefaultSessionHandler
     * @param config			
     * @param sessionHandler	
     */
    public GameMsgServer(GameServerConfig config, DefaultSessionHandler sessionHandler) {
    	createPort(config);
        
        createProtocolCodecFactory(config);
        
        createSessionHandler(config, sessionHandler);

        createFactory(config);
    }
    
	private void createPort(GameServerConfig config) {
		this.serverPort = config.getServerPort();

        tcpServer = new TCPServer();
        tcpServer.setServerPort(serverPort);
	}

	private void createProtocolCodecFactory(GameServerConfig config) {
		ProtocolCodecFactory codecFactory = new DefaultProtocolCodecFactory(
                config.getMaxMsgDataSize(), 
                config.getEncodeBufferSize());
        tcpServer.setProtocolCodeFactory(codecFactory);
	}
    
    private void createSessionHandler(GameServerConfig config, DefaultSessionHandler sessionHandler) {
    	if(sessionHandler == null){
    		sessionHandler = new DefaultSessionHandler();
            sessionHandler.setPipeline(config.getActionDispatcher());
    	}
        tcpServer.setSessionHandler(sessionHandler);
	}

	private void createFactory(GameServerConfig config) {
		MessageFactory.getInstance().init(config.getMessagePackages(), config.getMessageSuffixes());
        ActionFactory.getInstance().init(config.getActionPackages(), config.getActionSuffixes());
	}

    /**
     * 启动服务器。
     */
    public void start() {
        try {
            tcpServer.start();
            logger.error("游戏连接服务启动! " + this.serverPort);
        } catch (IOException e) {
            logger.error("游戏连接服务启动失败!", e);
        }
    }

    /**
     * 关闭服务器。
     */
    public void stop() {
        tcpServer.stop();
        logger.error("游戏连接服务关闭! " + this.serverPort);
    }
    
}
