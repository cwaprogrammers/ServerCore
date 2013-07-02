package com.cwa.util.automsg;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.AbstractMessage;
import com.cwa.util.automsg.codec.ReflectiveCodecRepository;

/**
 * AutoMessage利用反射自动实现消息编码和解码。
 * 子类只需要用注解标准消息字段即可。
 */
public abstract class AutoMessage extends AbstractMessage {

    public AutoMessage(int cmdId) {
        super(cmdId);
    }

    @Override
    public void decodeBody(GameInput in) {
        ReflectiveCodecRepository.getCodec(getClass()).decodeBody(in, this);
    }

    @Override
    public void encodeBody(GameOutput out) {
        ReflectiveCodecRepository.getCodec(getClass()).encodeBody(out, this);
    }
    
}
