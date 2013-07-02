package com.cwa.gameblob;

import org.junit.Assert;
import org.junit.Test;

public class IntegerBlobCodecTest {
    
    @Test
    public void testIntegerBlobCodec() {
        IntegerBlobCodec codec = new IntegerBlobCodec();
        
        byte[] blob = codec.encode(98);
        Assert.assertEquals(4, blob.length);
        
        int x = codec.decode(blob);
        Assert.assertEquals(98, x);
    }
    
}
