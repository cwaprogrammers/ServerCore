package com.cwa.gameblob;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;

// 增加password
public class TestCodecV1 extends BlobCodec<TestObject> {

    @Override
    public TestObject read(GameInput in) {
        TestObject obj = new TestObject();
        obj.setUserId(in.getInt());
        obj.setUserName(in.getString());
        obj.setPassword(in.getString());
        return obj;
    }

    @Override
    public void write(TestObject obj, GameOutput out) {
        out.putInt(obj.getUserId());
        out.putString(obj.getUserName());
        out.putString(obj.getPassword());
    }
    
}
