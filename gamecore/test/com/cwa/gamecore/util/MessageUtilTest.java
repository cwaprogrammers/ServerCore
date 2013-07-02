package com.cwa.gamecore.util;

import com.cwa.gamecore.message.MessageFactory;
import com.cwa.gamecore.message.TestMessage;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MessageUtilTest {
    
    private TestMessage msg;
    
    @BeforeClass
    public static void initMessageFactory() {
        MessageFactory.getInstance().init(Arrays.asList("com.cwa"));
    }
    
    @Before
    public void initTestMessage() {
         msg = new TestMessage();
         msg.setId(72);
    }
    
    @Test
    public void testEncodeDecode() {
        byte[] bytes = MessageUtil.encode(msg);
        //System.out.println(Arrays.toString(bytes));
        TestMessage decodedMsg = (TestMessage) MessageUtil.decode(bytes);
        assertEquals(msg.getCommandId(), decodedMsg.getCommandId());
        assertEquals(msg.getId(), decodedMsg.getId());
    }
    
    @Test
    public void testDecode_noEnoughData() {
        byte[] bytes = {0x23, 0x42};
        try {
            MessageUtil.decode(bytes);
            fail("应该抛出异常！");
        } catch (Exception e) {
            assertEquals("No enough data", e.getMessage().substring(0, 14));
        }
    }
    
    @Test
    public void testDecode_wrongMagicNumbers() {
        byte[] bytes = {0x23, 0x42, 0, 0, 0, 0};
        try {
            MessageUtil.decode(bytes);
            fail("应该抛出异常！");
        } catch (Exception e) {
            assertEquals("Bad message magic number", e.getMessage().substring(0, 24));
        }
    }
    
    @Test
    public void testDecode_wrongMessageLength() {
        byte[] bytes = {0x23, 0x24, 0, 3, 0, 0};
        try {
            MessageUtil.decode(bytes);
            fail("应该抛出异常！");
        } catch (Exception e) {
            assertEquals("Wrong message length", e.getMessage().substring(0, 20));
        }
    }
    
    @Test
    public void testDecode_wrongCommandID() {
        byte[] bytes = {0x23, 0x24, 0, 2, 9, 9};
        try {
            MessageUtil.decode(bytes);
            fail("应该抛出异常！");
        } catch (Exception e) {
            assertEquals("Unknown command ID", e.getMessage().substring(0, 18));
        }
    }
    
    @Test
    public void testDecode_tooManyData() {
        byte[] bytes = {0x23, 0x24, 0, 8, 0, 2, 0, 0, 0, 0, 1, 1};
        try {
            MessageUtil.decode(bytes);
            fail("应该抛出异常！");
        } catch (Exception e) {
            assertEquals("Error decoding TestMessage, superfluous bytes: 2", e.getMessage());
        }
    }
    
    
}
