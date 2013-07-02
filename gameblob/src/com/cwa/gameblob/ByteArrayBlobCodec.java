package com.cwa.gameblob;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;

public class ByteArrayBlobCodec extends BlobCodec<byte[]> {

    @Override
    public byte[] read(GameInput in) {
        return in.getBytes();
    }

    @Override
    public void write(byte[] data, GameOutput out) {
        out.putBytes(data);
    }
    
}
