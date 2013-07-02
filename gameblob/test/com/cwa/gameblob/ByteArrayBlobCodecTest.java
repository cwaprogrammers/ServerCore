package com.cwa.gameblob;

import org.junit.Assert;
import org.junit.Test;

public class ByteArrayBlobCodecTest {
    
    @Test
    public void testByteArrayBlobCodec() {
        ByteArrayBlobCodec codec = new ByteArrayBlobCodec();
        
        byte[] blob = codec.encode("abcd".getBytes());
        Assert.assertEquals(6, blob.length);
        
        byte[] x = codec.decode(blob);
        Assert.assertEquals("abcd", new String(x));
    }
    
}
