package com.cwa.client.mina;

import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.util.MessageUtil;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ClientMsgEncoder implements ProtocolEncoder {
    
    @Override
    public void encode(IoSession session, Object msg, ProtocolEncoderOutput peo) throws Exception {
        GameMessage request = (GameMessage) msg;
        IoBuffer buf = IoBuffer.wrap(MessageUtil.encode(request));
        peo.write(buf);
    }

    @Override
    public void dispose(IoSession session) throws Exception {
        // 
    }
    
}
