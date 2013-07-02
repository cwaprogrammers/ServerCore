package com.cwa.gameblob;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import java.util.List;

public class ListBlobCodec<T> extends BlobCodec<List<T>> {
    
    private final BlobCodec<T> codec;

    public ListBlobCodec(BlobCodec<T> codec) {
        this.codec = codec;
    }

    @Override
    public List<T> read(GameInput in) {
        return codec.readList(in);
    }

    @Override
    public void write(List<T> list, GameOutput out) {
        codec.writeList(list, out);
    }
    
}
