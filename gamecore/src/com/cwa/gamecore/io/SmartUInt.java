package com.cwa.gamecore.io;

/**
 * 这个类往GameOutput中写入，或者从GameInput里读出一个正整数n（0 ~ 2147483647）。
 * 
 * 如果n位于区间 [   0 , 2 ^7-1] 则占用1个字节
 * 如果n位于区间 [2^ 7 , 2^14-1] 则占用2个字节
 * 如果n位于区间 [2^14 , 2^21-1] 则占用3个字节
 * 如果n位于区间 [2^21 , 2^28-1] 则占用4个字节
 * 如果n位于区间 [2^28 , 2^31-1] 则占用5个字节
 * 
 */
public class SmartUInt {
    
    /**
     * 往GameOutput中写入一个SmartUInt。
     * @param in
     * @param n 
     */
    public static void put(GameOutput out, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n < 0:" + n);
        }
        
        if (n < 128) {
            out.put((byte) n);
            return;
        }
        
        while (n > 0) {
            int head = n & 0x7F; // right 7 bits
            n = n >> 7; // left bits
            if (n > 0) {
                out.put((byte) (head | 0x80));
            } else {
                out.put((byte) head);
                break;
            }
        }
    }
    
    /**
     * 从GameInput中读出一个SmartUInt。
     * @param out
     * @return 
     */
    public static int get(GameInput in) {
        int b = in.get();
        if (b >= 0) {
            return b;
        }
        
        int n = b & 0x7F;
        for (int i = 1; i <= 4; i++) {
            b = in.get();
            n = n | ((b & 0x7F) << (7 * i));
            if (b > 0) {
                break;
            }
        }
        
        return n;
    }
    
}
