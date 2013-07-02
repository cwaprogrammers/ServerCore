package com.cwa.gamecore.message;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;

public class TestMessage extends AbstractMessage {

    private int id;
    
    public TestMessage() {
        super(2);
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
