package com.cwa.util.automsg;

import com.cwa.gamecore.io.ByteArrayGameInput;
import com.cwa.gamecore.io.ByteArrayGameOutput;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class AutoMsgCodecTest {
    
    @Test
    public void testSimpleMessage() {
        SimpleMessage msg = new SimpleMessage();
        msg.setBool(true);
        msg.setByteInt((byte)99);
        msg.setShortInt((short)21);
        msg.setIntInt(1024);
        msg.setLongInt(System.currentTimeMillis());
        msg.setStr("abc##$$!010");
        msg.setBytes("cwa".getBytes());
        
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        msg.encodeBody(out);
        
        ByteArrayGameInput in = new ByteArrayGameInput(out.toByteArray());
        SimpleMessage msg2 = new SimpleMessage();
        msg2.decodeBody(in);
        
        Assert.assertEquals(msg.isBool(), msg2.isBool());
        Assert.assertEquals(msg.getByteInt(), msg2.getByteInt());
        Assert.assertEquals(msg.getShortInt(), msg2.getShortInt());
        Assert.assertEquals(msg.getIntInt(), msg2.getIntInt());
        Assert.assertEquals(msg.getLongInt(), msg2.getLongInt());
        Assert.assertEquals(msg.getStr(), msg2.getStr());
        Assert.assertEquals(new String(msg.getBytes()), new String(msg2.getBytes()));
    }
    
    @Test
    public void testDependencyMessageOn() {
        DependencyMsg msg = new DependencyMsg();
        msg.setBool(true);
        msg.setIntVal(99);
        
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        msg.encodeBody(out);
        
        ByteArrayGameInput in = new ByteArrayGameInput(out.toByteArray());
        DependencyMsg msg2 = new DependencyMsg();
        msg2.decodeBody(in);
        
        Assert.assertEquals(msg.isBool(), msg2.isBool());
        Assert.assertEquals(msg.getIntVal(), msg2.getIntVal());
    }
    
    @Test
    public void testDependencyMessageOff() {
        DependencyMsg msg = new DependencyMsg();
        msg.setBool(false);
        msg.setIntVal(99);
        
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        msg.encodeBody(out);
        
        ByteArrayGameInput in = new ByteArrayGameInput(out.toByteArray());
        DependencyMsg msg2 = new DependencyMsg();
        msg2.decodeBody(in);
        
        Assert.assertEquals(msg.isBool(), msg2.isBool());
        Assert.assertEquals(0, msg2.getIntVal());
    }
    
    @Test
    public void test() throws Exception {
        ListMessage msg = new ListMessage();
        msg.setBool(true);
        msg.setList(Arrays.asList(
                new ListElem(true, 1), 
                new ListElem(true, 2),
                new ListElem(false, 100)
        ));
        
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        msg.encodeBody(out);
        
        ByteArrayGameInput in = new ByteArrayGameInput(out.toByteArray());
        ListMessage msg2 = new ListMessage();
        msg2.decodeBody(in);
        
        Assert.assertEquals(msg.isBool(), msg2.isBool());
        Assert.assertEquals(msg.getList().size(), msg2.getList().size());
        
        Assert.assertEquals(1, msg2.getList().get(0).getIntVal());
        Assert.assertEquals(2, msg2.getList().get(1).getIntVal());
        Assert.assertEquals(100, msg2.getList().get(2).getIntVal());
    }
    
}
