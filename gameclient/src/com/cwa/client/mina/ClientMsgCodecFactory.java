package com.cwa.client.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ClientMsgCodecFactory implements ProtocolCodecFactory {
    
    private static final ClientMsgEncoder encoder = new ClientMsgEncoder();
    private static final ClientMsgDecoder decoder = new ClientMsgDecoder();
    
    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }
    
}
