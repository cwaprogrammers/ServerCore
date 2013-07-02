package com.cwa.gamecore.io;

import org.junit.Assert;
import org.junit.Test;

public class UShortTest {
    
    @Test(expected=IllegalArgumentException.class)
    public void putGetNegativeNum() {
        testPutGet(-1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void putGet65536() {
        testPutGet(65536);
    }
    
    @Test
    public void putGet() {
        testPutGet(0);
        testPutGet(1);
        testPutGet(32767); // Short.MAX_VALUE
        testPutGet(32768);
        testPutGet(33333);
        testPutGet(65535);
    }
    
    private void testPutGet(int n) {
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        UShort.put(out, n);
        byte[] bytes = out.toByteArray();
        Assert.assertTrue(bytes.length == 2);
        Assert.assertEquals(n, UShort.get(new ByteArrayGameInput(bytes)));
    }
    
}
