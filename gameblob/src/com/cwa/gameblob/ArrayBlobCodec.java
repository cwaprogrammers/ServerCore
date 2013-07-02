package com.cwa.gameblob;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import java.lang.reflect.Array;

public class ArrayBlobCodec<T> extends BlobCodec<T[]> {
    
    private final Class<T> componentClass;
    private final BlobCodec<T> codec;

    public ArrayBlobCodec(BlobCodec<T> codec, Class<T> componentClass) {
        this.componentClass = componentClass;
        this.codec = codec;
    }

    @Override
    public T[] read(GameInput in) {
        int length = in.getShort();
        T[] array = (T[]) Array.newInstance(componentClass, length);
        
        for (int i = 0; i < length; i++) {
            array[i] = codec.read(in);
        }
        
        return array;
    }

    @Override
    public void write(T[] array, GameOutput out) {
        out.putShort((short) array.length);
        for (T t : array) {
            codec.write(t, out);
        }
    }
    
}
