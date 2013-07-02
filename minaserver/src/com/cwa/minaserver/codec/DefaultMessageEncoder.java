/**
 * 2011-6-10 上午10:34:25
 */
package com.cwa.minaserver.codec;

import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.message.GameResponse;
import com.cwa.minaserver.io.MinaGameOutput;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;


/**
 * @author zpc
 * @descirption 消息编码器
 */
public class DefaultMessageEncoder implements ProtocolEncoder {

        private static Logger logger = Logger.getLogger(DefaultMessageEncoder.class);
        
        private final int encodeBufferSize;

        public  DefaultMessageEncoder(int encodeBufferSize) {
            this.encodeBufferSize = encodeBufferSize;
        }

        @Override
        public void dispose(IoSession session) throws Exception {
        }

        @Override
        public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
                IoBuffer buffer = encodeIoBuffer((GameResponse) message);
                if (logger.isDebugEnabled()) {
                        debugOutputMessage(logger, buffer);
                }
                out.write(buffer);
        }
        
        /**
         * 消息内容封装到IoBuffer 可以获取真实的消息体长度和消息包总长度
         * 
         * @return
         */
        public IoBuffer encodeIoBuffer(GameResponse message) throws Exception {
                try {
                        int totalLength;
                        // 创建IOBuffer
                        // TODO 这里可以优化 - 应该估计消息的长度
                        IoBuffer buffer = IoBuffer.allocate(encodeBufferSize);
                        buffer.setAutoExpand(true);
                        // 设置协议头标志
                        buffer.put(GameMessage.MAGIC_NUMBERS);
                        // 设置协议头数据
                        buffer.putShort((short) 0);
                        buffer.putShort((short) message.getCommandId());
                        // 设置协议体数据
                        message.encodeBody(new MinaGameOutput(buffer));
                        totalLength = buffer.position();
                        // 设置消息体大小,减去消息头的长度，以及记录长度的字段长度
                        totalLength -= (GameMessage.MAGIC_NUMBERS.length + 2);
                        // 修改消息体数据长度
                        buffer.putShort(GameMessage.MAGIC_NUMBERS.length, (short) totalLength);
                        // 设置消息总大小
                        //bufferLength = buffer.position();
                        buffer.flip();
                        
                        return buffer;
                } catch (Exception e) {
                        logger.error("指令编码发生错误，指令编号[" + message.getCommandId() + "]", e);
                        throw e;
                }
        }
        
		private void debugOutputMessage(Logger logger, IoBuffer buffer) {
			logger.debug("发送消息体：" + buffer);
                  
			String sendMsg = "发送消息详情：";
			for(int i=0;i < buffer.limit();i++){
			        sendMsg += (" "+ Integer.toHexString(buffer.get(i)));
			}
			logger.debug(sendMsg);
		}
        
}
