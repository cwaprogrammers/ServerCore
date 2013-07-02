package com.cwa.gamecore.util;

import org.junit.Assert;
import org.junit.Test;

public class CompressionUtilTest {
    
    @Test
    public void gzip() {
        byte[] data = "aaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbb+111111111111111111.......zzz".getBytes();
        byte[] gzipData = CompressionUtil.gzip(data);
        byte[] unGzipData = CompressionUtil.unGzip(gzipData);
        
        Assert.assertArrayEquals(data, unGzipData);
    }
    
}
