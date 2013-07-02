package com.cwa.httpserver;

import com.cwa.gamecore.dispatcher.ActionDispatcher;
import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.message.GameResponse;
import com.cwa.gamecore.util.MessageUtil;
import com.cwa.gamecore.util.StreamUtil;
import com.cwa.gamecore.util.StringUtil;
import com.cwa.httpserver.session.HttpGameSession;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * 处理客户端消息的Servlet。
 */
public class GameRpcServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(GameRpcServlet.class);
    
    private static final String MSG_KEY = "msg";
    
    private ActionDispatcher dispatcher;

    public GameRpcServlet(ActionDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Handle GET request...");
        
        try {
            byte[] msgBytes = getMsgBytesFromGet(req);
            GameRequest reqMsg = decodeRequest(msgBytes);

            GameResponse respMsg = execAction(reqMsg);

            byte[] respBytes = encodeResponse(respMsg);
            String respStr = StringUtil.toBase64String(respBytes);

            resp.getWriter().write(respStr);
        } catch (ServletException e) {
            logger.error("Error handle GET request", e);
            throw e;
        } catch (IOException e) {
            logger.error("Error handle GET request", e);
            throw e;
        } catch (Exception e) {
            logger.error("Error handle GET request", e);
            throw new ServerException(e.getMessage(), e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Handle POST request...");
        
        try {
            byte[] msgBytes = getMsgBytesFromPost(req);
            GameRequest reqMsg = decodeRequest(msgBytes);

            GameResponse respMsg = execAction(reqMsg);

            byte[] respBytes = encodeResponse(respMsg);

            resp.getOutputStream().write(respBytes);
        } catch (IOException e) {
            logger.error("Error handle POST request", e);
            throw e;
        } catch (Exception e) {
            logger.error("Error handle POST request", e);
            throw new ServerException(e.getMessage(), e);
        }
    }
    
    // 从GET请求中提取消息数据
    private byte[] getMsgBytesFromGet(HttpServletRequest req) throws ServletException {
        String msgStr = req.getParameter(MSG_KEY);
        if (msgStr == null || msgStr.isEmpty()) {
            throw new ServletException("Parameter not found: " + MSG_KEY);
        }
        
        // Base64包含加号（+），可能会被转换成空格。
        msgStr = msgStr.replace(' ', '+');
        
        logger.debug("msg=" + msgStr);
        return StringUtil.decodeBase64String(msgStr);
    }
    
    // 从POST请求中提取消息数据
    private byte[] getMsgBytesFromPost(HttpServletRequest req) throws IOException {
        InputStream in = req.getInputStream();
        return StreamUtil.drain(in);
    }
    
    // 解码请求消息
    private GameRequest decodeRequest(byte[] msgBytes) throws IOException {
        if (logger.isTraceEnabled()) {
            logger.trace("Request data:" + StringUtil.toDebugString(msgBytes));
        }
        
        return MessageUtil.decode(msgBytes);
    }
    
    // 编码响应消息
    private byte[] encodeResponse(GameResponse resp) {
        byte[] respBytes = MessageUtil.encode(resp);
        
        if (logger.isTraceEnabled()) {
            logger.trace("Response data:" + StringUtil.toDebugString(respBytes));
        }
        
        return respBytes;
    }
    
    // 执行Action
    private GameResponse execAction(GameRequest msg) {
        logger.debug("Start execute action " + msg.getCommandId());
        
        HttpGameSession session = new HttpGameSession();
        dispatcher.dispatchAction(session, msg);
        
        logger.debug("Finish execute action " + msg.getCommandId());
        return session.getResponse();
    }
    
}
