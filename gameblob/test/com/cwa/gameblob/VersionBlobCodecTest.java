package com.cwa.gameblob;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;

public class VersionBlobCodecTest {
    
    @Test
    public void testVersionBlobCodec() {
        TestObject obj = new TestObject();
        obj.setUserId(19);
        obj.setUserName("spiderman");
        obj.setPassword("p4wd");
        
        VersionBlobCodec<TestObject> codec0 = new VersionBlobCodec<TestObject>(Arrays.asList(new TestCodecV0()));
        VersionBlobCodec<TestObject> codec1 = new VersionBlobCodec<TestObject>(Arrays.asList(new TestCodecV0(), new TestCodecV1()));
        
        byte[] blobV0 = codec0.encode(obj);
        TestObject obj0 = codec0.decode(blobV0);
        assertEquals(19, obj0.getUserId());
        assertNull(obj0.getPassword());
        TestObject obj1 = codec1.decode(blobV0);
        assertEquals(19, obj1.getUserId());
        assertNull(obj1.getPassword());
        
        byte[] blobV1 = codec1.encode(obj);
        obj1 = codec1.decode(blobV1);
        assertEquals(19, obj1.getUserId());
        assertEquals("p4wd", obj1.getPassword());
        
        try {
            codec0.decode(blobV1);
            fail("应该抛出异常");
        } catch (RuntimeException expected) {}
    }
    
}
