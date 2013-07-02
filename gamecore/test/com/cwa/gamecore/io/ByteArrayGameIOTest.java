package com.cwa.gamecore.io;

import java.util.Date;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ByteArrayGameIOTest {
    
    private ByteArrayGameOutput out;
    
    @Before
    public void createOutput() {
        out = new ByteArrayGameOutput();
    }
    
    @Test
    public void putGetTrue() {
        out.putBoolean(true);
        assertEquals(1, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(true, in.getBoolean());
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void putGetFalse() {
        out.putBoolean(false);
        assertEquals(1, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(false, in.getBoolean());
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void putGetByte() {
        out.put((byte)9);
        assertEquals(1, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(9, in.get());
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void putGetShort() {
        out.putShort((short)9);
        assertEquals(2, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(9, in.getShort());
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void putGetInt() {
        out.putInt(9);
        assertEquals(4, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(9, in.getInt());
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void putGetLong() {
        out.putLong(9);
        assertEquals(8, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(9, in.getLong());
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void pubGetBytes() {
        out.putBytes("abc123".getBytes());
        assertEquals(2+6, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals("abc123", new String(in.getBytes()));
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void pubGetNullBytes() {
        out.putBytes(null);
        assertEquals(2, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(0, in.getBytes().length);
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void pubGetString() {
        out.putString("abc123");
        assertEquals(2+6, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        String x = in.getString();
        assertEquals("abc123", x);
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void pubGetChineseString() {
        out.putString("abc程序员123。");
        //assertEquals(2+6, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        String x = in.getString();
        assertEquals("abc程序员123。", x);
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void pubGetNullString() {
        out.putString(null);
        assertEquals(2, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals("", in.getString());
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void pubGetEmptyString() {
        out.putString("");
        assertEquals(2, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals("", in.getString());
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void putNullDate() {
        out.putDate(null);
        assertThat(out.toByteArray().length, is(4));
        
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertThat(in.getDate().getTime(), is((long)0));
        assertThat(in.remaining(), is(0));
    }
    
    @Test
    public void putDate() {
        Date date = new Date();
        out.putDate(date);
        assertThat(out.toByteArray().length, is(4));
        
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertThat(in.getDate().getTime()/1000, is(date.getTime()/1000));
        assertThat(in.remaining(), is(0));
    }
    
    @Test
    public void putGetFloat() {
        out.putFloat(9.8f);
        assertEquals(4, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(9.8f, in.getFloat(), 0.0f);
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void putGetDouble() {
        out.putDouble(9.8d);
        assertEquals(8, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(9.8d, in.getDouble(), 0.0d);
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void putGetChar() {
        out.putChar('c');
        assertEquals(2, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals('c', in.getChar());
        assertEquals(0, in.remaining());
    }
    
    @Test
    public void putGetGzip() {
        String data =
"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "0000000000000000000000000000000000000000000000000000000000000000000000000000";
        
        out.putGzip(data.getBytes());
        assertEquals(29, out.toByteArray().length);
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(data, new String(in.getGzip()));
        assertEquals(0, in.remaining());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void putGetRawBytes_nullArg() {
        out.putRawBytes(null);
    }
    
    @Test
    public void putGetRawBytes() {
        String data = "raw+bytes";
        
        out.putRawBytes(data.getBytes());
        assertEquals(9, out.toByteArray().length);
        
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        assertEquals(data, new String(in.getRawBytes(9)));
        assertEquals(0, in.remaining());
    }
    
    @Test(expected=RuntimeException.class)
    public void readBytes_notEnoughData() {
        out.putRawBytes("abc".getBytes());
        assertEquals(3, out.toByteArray().length);
        
        GameInput in = new ByteArrayGameInput(out.toByteArray());
        in.getRawBytes(5);
    }
    
}
