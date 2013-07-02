package com.cwa.gamecore.util;

import com.cwa.gamecore.io.FastByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;

public class StreamUtilTest {
    
    @Test
    public void drain() throws IOException {
        byte[] data = "abcdabcd1234afasdfasdf./asq9w7385o34yjnlkjsdfbnovidsy798t".getBytes();
        InputStream is = new FastByteArrayInputStream(data);
        byte[] data2 = StreamUtil.drain(is);
        
        Assert.assertArrayEquals(data, data2);
    }
    
}
