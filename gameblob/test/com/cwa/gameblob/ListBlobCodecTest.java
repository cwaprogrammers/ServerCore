package com.cwa.gameblob;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ListBlobCodecTest {
    
    @Test
    public void testListBlobCodec() {
        List<TestObject> list = new ArrayList<TestObject>();
        for (int i = 0; i < 10; i++) {
            TestObject obj = new TestObject();
            obj.setUserId(i);
            obj.setUserName("user" + i);
            list.add(obj); 
        }
        
        ListBlobCodec<TestObject> codec = new ListBlobCodec<TestObject>(new TestCodecV0());
        byte[] blob = codec.encode(list);
        List<TestObject> list2 = codec.decode(blob);
        assertEquals(10, list2.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(i, list2.get(i).getUserId());
        }
    }
    
}
