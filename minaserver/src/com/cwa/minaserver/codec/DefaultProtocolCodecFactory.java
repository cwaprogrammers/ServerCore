package com.cwa.minaserver.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class DefaultProtocolCodecFactory implements ProtocolCodecFactory {

    private final ProtocolDecoder decoder;
    private final ProtocolEncoder encoder;
    
    public DefaultProtocolCodecFactory(int maxMsgDataSize, int encodeBufferSize) {
        decoder = new DefaultMessageDecoder(maxMsgDataSize);
        encoder = new DefaultMessageEncoder(encodeBufferSize);
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }
    
}
