package com.cwa.gamecore.io;

/**
 * 无符号短整数（short）。
 */
public class UShort {
    
    // 往流里写一个无符号短整数
    public static void put(GameOutput out, int n) {
        if (n < 0 || n > 65535) {
            throw new IllegalArgumentException("n < 0 || n >= 65535 :" + n);
        }
        
        out.putShort((short) n);
    }
    
    // 从流里读一个无符号短整数
    public static int get(GameInput in) {
        short n = in.getShort();
        if (n >= 0) {
            return n;
        }
        
        return 0x8000 + 0x7FFF & n;
    }
    
}
