package com.cwa.gameblob;

import com.cwa.gamecore.GameException;
import com.cwa.gamecore.io.ByteArrayGameInput;
import com.cwa.gamecore.io.ByteArrayGameOutput;
import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * 大字段编码解码器。
 * @param <T> 
 */
public abstract class BlobCodec<T> {
    
    /**
     * 解码游戏对象。
     * @param in
     * @return 
     */
    public abstract T read(GameInput in);
    
    /**
     * 编码游戏对象。
     * @param object
     * @param out 
     */
    public abstract void write(T object, GameOutput out);
    
    /**
     * 解码List。
     * @param in
     * @return 
     */
    public final List<T> readList(GameInput in) {
        int size = in.getShort();
        List<T> list = new ArrayList<T>(size);
        
        for (int i = 0; i < size; i++) {
            list.add(read(in));
        }
        
        return list;
    }
    
    /**
     * 编码List。
     * @param list
     * @param out 
     */
    public final void writeList(List<T> list, GameOutput out) {
        out.putShort((short) list.size());
        for (T t : list) {
            write(t, out);
        }
    }
    
    /**
     * 将对象编码为BLOB。
     * @param obj
     * @return 
     */
    public final byte[] encode(T obj) {
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        write(obj, out);
        return out.toByteArray();
    }
    
    /**
     * 将字节数组解码为对象。
     * @param blob
     * @return 
     */
    public final T decode(byte[] blob) {
        ByteArrayGameInput in = new ByteArrayGameInput(blob);
        T obj = read(in);
        
        if (in.remaining() > 0) {
            throw new GameException("Decode failed, remaining bytes: " + in.remaining());
        }
        
        return obj;
    }
    
}
