package com.cwa.gamecore.io;

import com.cwa.gamecore.util.CompressionUtil;
import java.util.Date;

public abstract class AbstractGameInput implements GameInput {

    @Override
    public Date getDate() {
        long time = getInt() * 1000L;
        return new Date(time);
    }

    @Override
    public byte[] getGzip() {
        byte[] gzip = getBytes();
        return CompressionUtil.unGzip(gzip);
    }
    
}
