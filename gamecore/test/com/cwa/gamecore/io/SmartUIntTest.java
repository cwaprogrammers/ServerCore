package com.cwa.gamecore.io;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

public class SmartUIntTest {
    
    @Test
    public void putGet() {
        // 1 bytes
        for (int i = 0; i < 128; i++) {
            testPutGet(i, 1);
        }
        // 2 bytes
        for (int i = 128; i < 128*128; i++) {
            testPutGet(i, 2);
        }
        // 3 bytes
        testPutGet(128*128, 3);
        testPutGet(128*128*128-1, 3);
        // 4 bytes
        testPutGet(128*128*128, 4);
        testPutGet(128*128*128*128-1, 4);
        // 5 bytes
        testPutGet(128*128*128*128, 5);
        testPutGet(Integer.MAX_VALUE, 5);
    }
    
    @Test
    public void putGetRandomNumbers() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 2000; i++) {
            int n = random.nextInt(Integer.MAX_VALUE);
            if (n < 128) {
                testPutGet(n, 1);
            } else if (n < 128*128) {
                testPutGet(n, 2);
            } else if (n < 128*128*128) {
                testPutGet(n, 3);
            } else if (n < 128*128*128*128) {
                testPutGet(n, 4);
            } else if (n < Integer.MAX_VALUE) {
                testPutGet(n, 5);
            }
        }
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void putGetNegative() {
        testPutGet(-1, 0);
    }
    
    private static void testPutGet(int n, int byteCount) {
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        SmartUInt.put(out, n);
        byte[] bytes = out.toByteArray();
        Assert.assertEquals(""+n, byteCount, bytes.length);
        Assert.assertEquals(""+n, n, SmartUInt.get(new ByteArrayGameInput(bytes)));
    }
    
}
