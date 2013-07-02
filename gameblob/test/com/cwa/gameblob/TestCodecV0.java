package com.cwa.gameblob;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;

public class TestCodecV0 extends BlobCodec<TestObject> {

    @Override
    public TestObject read(GameInput in) {
        TestObject obj = new TestObject();
        obj.setUserId(in.getInt());
        obj.setUserName(in.getString());
        return obj;
    }

    @Override
    public void write(TestObject obj, GameOutput out) {
        out.putInt(obj.getUserId());
        out.putString(obj.getUserName());
    }
    
}
