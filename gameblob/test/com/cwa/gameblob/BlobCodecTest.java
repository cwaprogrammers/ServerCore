package com.cwa.gameblob;

import com.cwa.gamecore.GameException;
import com.cwa.gamecore.io.ByteArrayGameOutput;
import org.junit.Assert;
import org.junit.Test;

public class BlobCodecTest {
    
    @Test
    public void testEncodeDecode() {
        TestObject obj = new TestObject();
        obj.setUserId(19);
        obj.setUserName("spiderman");
        
        TestCodecV0 codec = new TestCodecV0();
        
        byte[] blob = codec.encode(obj);
        TestObject obj2 = codec.decode(blob);
        
        Assert.assertEquals(19, obj2.getUserId());
        Assert.assertEquals("spiderman", obj2.getUserName());
    }
    
    @Test
    public void testDecodeFail_remaininggBytes() {
        TestCodecV0 codec = new TestCodecV0();
        
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        codec.write(new TestObject(), out);
        out.putInt(0);
        
        try {
            codec.decode(out.toByteArray());
            Assert.fail("应该抛出异常！");
        } catch (GameException e) {
            Assert.assertEquals("Decode failed, remaining bytes: 4", e.getMessage());
        }
    }
    
}
