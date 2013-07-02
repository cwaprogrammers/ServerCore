package com.cwa.protobuf;

import com.cwa.gamecore.io.ByteArrayGameInput;
import com.cwa.gamecore.io.ByteArrayGameOutput;
import com.cwa.tribe.messages.Login;
import com.cwa.tribe.messages.MsgId;
import java.io.ByteArrayOutputStream;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProtobufGameMessageTest {
    
    @BeforeClass
    public static void register() {
        ProtobufSupport.register(MsgId.MsgID.MSG_LOGIN_VALUE, Login.LoginRequest.PARSER, null);
    }
    
    @Test
    public void decodeProtobufMessage() throws Exception {
        Login.LoginRequest req = Login.LoginRequest.newBuilder()
                .setMsgID(MsgId.MsgID.MSG_LOGIN)
                .setUsername("zxh")
                .setPassword("p4wd")
                .build();
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        req.writeTo(os);
        
        ByteArrayGameOutput gameOut = new ByteArrayGameOutput();
        gameOut.putBytes(os.toByteArray());
        
        ProtobufGameMessage gameMessage = new ProtobufGameMessage();
        gameMessage.decodeBody(new ByteArrayGameInput(gameOut.toByteArray()));
        Assert.assertNotNull(gameMessage.getMessage());
        Login.LoginRequest req2 = (Login.LoginRequest) gameMessage.getMessage();
        Assert.assertEquals(req2.getUsername(), "zxh");
        Assert.assertEquals(req2.getPassword(), "p4wd");
    }
    
}
