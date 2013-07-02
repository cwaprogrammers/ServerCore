package com.cwa.gameblob;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ArrayBlobCodecTest {
    
    @Test
    public void testArrayBlobCodec() {
        TestObject[] array = new TestObject[10];
        for (int i = 0; i < 10; i++) {
            TestObject obj = new TestObject();
            obj.setUserId(i);
            obj.setUserName("user" + i);
            array[i] = obj; 
        }
        
        ArrayBlobCodec<TestObject> codec = new ArrayBlobCodec<TestObject>(new TestCodecV0(), TestObject.class);
        byte[] blob = codec.encode(array);
        TestObject[] array2 = codec.decode(blob);
        assertEquals(10, array2.length);
        for (int i = 0; i < 10; i++) {
            assertEquals(i, array2[i].getUserId());
        }
    }
    
}
