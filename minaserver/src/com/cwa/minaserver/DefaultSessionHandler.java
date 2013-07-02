/**
 * 2011-6-14 上午10:27:55
 */
package com.cwa.minaserver;

import com.cwa.gamecore.dispatcher.ActionDispatcher;
import com.cwa.gamecore.message.GameRequest;
import com.cwa.minaserver.session.MinaGameSession;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * @author zpc
 * @descirption 功能描述
 */
public class DefaultSessionHandler extends IoHandlerAdapter {
        private final static Logger logger = Logger.getLogger(DefaultSessionHandler.class);

        private static final String Exception_Key = "ExceptionCounts";

        private ActionDispatcher pipeline;

        @Override
        public void sessionCreated(IoSession session) {
                session.setAttribute(Exception_Key, 0);
                if (logger.isDebugEnabled())
                        logger.debug("createted session!");
        }

        @Override
        public void sessionOpened(IoSession session) {
                if (logger.isDebugEnabled())
                        logger.debug("opened session!");
        }

        @Override
        public void sessionClosed(IoSession session) {
                if (logger.isDebugEnabled())
                        logger.debug("closed session!");
        }

        @Override
        public void messageReceived(IoSession session, Object message) {
                if (session == null) {
                        logger.error("session can not null.");
                        return;
                }

                if (!session.isConnected()) {
                        logger.error("session is not connected.");
                        session.close(true);
                        return;
                }
                
                if (logger.isDebugEnabled()) {
                        logger.debug("server receive message: " + message.toString() + "|" + session.getId());
                }
                
                try {
                        pipeline.dispatchAction(new MinaGameSession(session), (GameRequest) message);
                } catch (Exception e) {
                        logger.error("处理消息的时候发生异常", e);
                } finally {
                        logger.debug(session.hashCode() + " {RRRRR} icClosing=" + session.isClosing() + ",isConnected=" + session.isConnected() + ",isSuspended=" + session.isReadSuspended());
                }
        }

        @Override
        public void sessionIdle(IoSession session, IdleStatus status) {
                logger.info("sessionIdle: " + session);
        }

        @Override
        public void exceptionCaught(IoSession session, Throwable cause) {
                if (cause instanceof java.io.IOException)
                        return;
                logger.error(cause.getMessage(), cause);
        }

        public ActionDispatcher getPipeline() {
                return pipeline;
        }

        public void setPipeline(ActionDispatcher pipeline) {
                this.pipeline = pipeline;
        }
}// Class DefaultSessionHandler