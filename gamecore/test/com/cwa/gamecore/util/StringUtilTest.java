package com.cwa.gamecore.util;

import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class StringUtilTest {
    
    @Test
    public void toHexString() {
        assertEquals("01020363", StringUtil.toHexString(new byte[] {1,2,3,99}));
        assertTrue(Arrays.equals(new byte[] {1,2,3,99}, StringUtil.decodeHexString("01020363")));
    }
    
    @Test
    public void toBase64String() {
        assertEquals("AQIDYw==", StringUtil.toBase64String(new byte[] {1,2,3,99}));
        assertTrue(Arrays.equals(new byte[] {1,2,3,99}, StringUtil.decodeBase64String("AQIDYw==")));
    }
    
    @Test
    public void toDebugString() {
        byte[] data = "aaa dfasdfj  aewur50 9d327yozxcffvm osufaoem lsdfas//.;l';[pasdf~~".getBytes();
        data[0] = 0;
        System.out.println(StringUtil.toDebugString(data));
    }
    
}
