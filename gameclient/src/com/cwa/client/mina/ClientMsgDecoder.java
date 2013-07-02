package com.cwa.client.mina;

import com.cwa.client.message.ClientMessageFactory;
import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.util.MessageUtil;
import com.cwa.minaserver.io.MinaGameInput;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ClientMsgDecoder extends CumulativeProtocolDecoder {

    @Override
    protected boolean doDecode(IoSession session, IoBuffer input, ProtocolDecoderOutput pdo) throws Exception {
        GameMessage response = decode(input);
        
        if (response == null) {
            return false;
        } else {
            pdo.write(response);
            return true;
        }
    }
    
    private GameMessage decode(IoBuffer input) {
        if (input.remaining() < 6) {
            return null;
        }
        
        // read magic code
        input.getShort();
        
        if (!input.prefixedDataAvailable(2)) {
            input.rewind();
            return null;
        }
        
        input.rewind();
        return (GameMessage) MessageUtil.decode(
                new MinaGameInput(input) , ClientMessageFactory.getInstance());
    }
    
}
