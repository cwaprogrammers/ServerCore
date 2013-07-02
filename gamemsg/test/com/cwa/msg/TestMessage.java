package com.cwa.msg;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.AbstractMessage;

public class TestMessage extends AbstractMessage {

    private int id;
    
    public TestMessage() {
        super(100);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void decodeBody(GameInput in) {
        id = in.getInt();
    }

    @Override
    public void encodeBody(GameOutput out) {
        out.putInt(id);
    }
    
}
