/**
 * 2011-6-10 上午09:18:29
 */
package com.cwa.minaserver.codec;

import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.message.MessageFactory;
import com.cwa.minaserver.io.MinaGameInput;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author zpc
 * @descirption 消息解码器
 */
public class DefaultMessageDecoder extends CumulativeProtocolDecoder {
    
        private static Logger logger = Logger.getLogger(DefaultMessageDecoder.class);
        
        // 单例
        private final int maxMsgDataSize;
        
        protected DefaultMessageDecoder(int maxMsgDataSize) {
            this.maxMsgDataSize = maxMsgDataSize;
        }

        @Override
        protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
                if (in.remaining() < 6) {
                        logger.error("*** 消息长度不足 ***" + in);
                        return false;
                }

                in.mark();
                for (int i = 0; i < GameMessage.MAGIC_NUMBERS.length; i++) {
                        byte prefixed = in.get();
                        if (prefixed != GameMessage.MAGIC_NUMBERS[i]) {
                                logger.error("****强制关闭会话****，消息分隔符错误：" + prefixed + "!=" + GameMessage.MAGIC_NUMBERS[i]);
                                session.close(true);
                                return false;
                        }
                }
                if (in.prefixedDataAvailable(2, maxMsgDataSize)) {
                        short messageLength = in.getShort();
                        int startPos = in.position();
                        // byte serverId = in.get();
                        short commandId = in.getShort();

                        GameRequest message = MessageFactory.getInstance().getMessage(commandId);
                        if (message == null) {
                                logger.error("****强制关闭会话****，没有找到对应的消息[commandId=" + commandId + "]");
                                session.close(true);
                                return false;
                        }
                        if (logger.isDebugEnabled()) {
                                logger.error("#############处理消息：[commandId=" + commandId + "] " + in + "#############");
                        }
                        // message.setServerId(serverId);
                        //message.setTotalLength(messageLength);
                        //message.setCommandId(commandId);
                        try {
                                message.decodeBody(new MinaGameInput(in));
                                // 解析长度检查
                                int decodeLength = in.position() - startPos;
                                if (decodeLength != messageLength) {
                                        logger.error("[comandId=" + commandId + "] [decode Error] [decodeLength(" + decodeLength + ")!=(setLength(" + messageLength + ")");
                                        in.position(startPos + messageLength);

                                        if (decodeLength > messageLength) {
                                                logger.error("****强制关闭会话**** ， 消息体长度大于头信息里指定的长度[commandId=" + commandId + ",message=" + message + "]");
                                                session.close(true);
                                        }
                                }
                        } catch (Exception e) {
                                logger.error("****强制关闭会话**** ， 消息体解析错误[commandId=" + commandId + ",message=" + message + "]", e);
                                session.close(true);
                                return false;
                        }

                        out.write(message);
                        return true;
                } else {
                        short messageLength = in.getShort();
                        //int startPos = in.position();
                        // byte serverId = in.get();
                        short commandId = in.getShort();
                        logger.error("指令:" + commandId + ";消息长度:" + messageLength + ":****消息体数据不全****：" + in.remaining());
                }
                in.reset();
                return false;
        }

}
