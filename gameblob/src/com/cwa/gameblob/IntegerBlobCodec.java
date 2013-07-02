package com.cwa.gameblob;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;

public class IntegerBlobCodec extends BlobCodec<Integer> {

    @Override
    public Integer read(GameInput in) {
        return in.getInt();
    }

    @Override
    public void write(Integer val, GameOutput out) {
        out.putInt(val);
    }
    
}
