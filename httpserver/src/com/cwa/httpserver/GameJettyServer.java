package com.cwa.httpserver;

import com.cwa.gamecore.action.ActionFactory;
import com.cwa.gamecore.message.MessageFactory;
import javax.servlet.Servlet;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * 对Jetty容器的简单封装。
 * TODO 可能还需要配置更多的参数
 */
public class GameJettyServer {
    
    private static final Logger logger = Logger.getLogger(GameJettyServer.class);
    
    private static final String RPC_SERVLET_PATH = "/rpc/*";
    
    private int port; // HTTP监听端口
    private Server jettyServer;
    
    public GameJettyServer(GameServerConfig config) {
        this.port = config.getServerPort();
        
        jettyServer = new Server(port);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        jettyServer.setHandler(context);
        
        Servlet rpcServlet = new GameRpcServlet(config.getActionDispatcher());
        context.addServlet(new ServletHolder(rpcServlet), RPC_SERVLET_PATH);
        
        MessageFactory.getInstance().init(config.getMessagePackages(), config.getMessageSuffixes());
        ActionFactory.getInstance().init(config.getActionPackages(), config.getActionSuffixes());
    }
    
    public void start() throws Exception {
        logger.debug("Start HTTP server: " + getPort());
        jettyServer.start();
        jettyServer.join();
    }
    
    public void stop() throws Exception {
        logger.debug("Stop HTTP server.");
        jettyServer.stop();
        logger.debug("Server stopped.");
    }
    
    public int getPort() {
        return port;
    }
    
}
