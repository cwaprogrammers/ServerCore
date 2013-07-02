package com.cwa.minaserver.io;

import org.apache.mina.core.buffer.IoBuffer;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MinaGameIOTest {
    
    @Test
    public void putGetBoolean() {
        IoBuffer buf = IoBuffer.allocate(1);
        new MinaGameOutput(buf).putBoolean(true);
        buf.flip();
        assertEquals(true, new MinaGameInput(buf).getBoolean());
        
        buf.flip();
        new MinaGameOutput(buf).putBoolean(false);
        buf.flip();
        assertEquals(false, new MinaGameInput(buf).getBoolean());
    }
    
    @Test
    public void putGetByte() {
        IoBuffer buf = IoBuffer.allocate(1);
        new MinaGameOutput(buf).put((byte)9);
        buf.flip();
        assertEquals(9, new MinaGameInput(buf).get());
    }
    
    @Test
    public void putGetShort() {
        IoBuffer buf = IoBuffer.allocate(2);
        new MinaGameOutput(buf).putShort((short)9);
        buf.flip();
        assertEquals(9, new MinaGameInput(buf).getShort());
    }
    
    @Test
    public void putGetInt() {
        IoBuffer buf = IoBuffer.allocate(4);
        new MinaGameOutput(buf).putInt(9);
        buf.flip();
        assertEquals(9, new MinaGameInput(buf).getInt());
    }
    
    @Test
    public void putGetLong() {
        IoBuffer buf = IoBuffer.allocate(8);
        new MinaGameOutput(buf).putLong(9);
        buf.flip();
        assertEquals(9, new MinaGameInput(buf).getLong());
    }
    
    @Test
    public void pubGetBytes() {
        IoBuffer buf = IoBuffer.allocate(6+2);
        new MinaGameOutput(buf).putBytes("abc123".getBytes());
        buf.flip();
        assertEquals("abc123", new String(new MinaGameInput(buf).getBytes()));
    }
    
    @Test
    public void pubGetString() {
        IoBuffer buf = IoBuffer.allocate(6+2);
        new MinaGameOutput(buf).putString("abc123");
        buf.flip();
        assertEquals("abc123", new MinaGameInput(buf).getString());
    }
    
    @Test
    public void pubGetNullString() {
        IoBuffer buf = IoBuffer.allocate(2);
        
        new MinaGameOutput(buf).putString(null);
        buf.flip();
        assertEquals("", new MinaGameInput(buf).getString());
    }
    
    @Test
    public void pubGetEmptyString() {
        IoBuffer buf = IoBuffer.allocate(2);
        
        new MinaGameOutput(buf).putString("");
        buf.flip();
        assertEquals("", new MinaGameInput(buf).getString());
    }
    
}
