package com.cwa.msg;

import com.cwa.crypto.DigestUtil;
import com.cwa.crypto.RsaKeyHolder;
import com.cwa.crypto.RsaUtil;
import com.cwa.gamecore.action.ActionFactory;
import com.cwa.gamecore.io.ByteArrayGameInput;
import com.cwa.gamecore.io.ByteArrayGameOutput;
import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.message.MessageFactory;
import com.cwa.gamecore.session.CatchResponseGameSession;
import com.cwa.gamecore.util.MessageUtil;
import com.cwa.msg.batch.BatchRequest;
import com.cwa.msg.batch.BatchResponse;
import com.cwa.msg.compression.GzipRequest;
import com.cwa.msg.encryption.RsaRequest;
import com.cwa.msg.mac.Md5Request;
import com.cwa.msg.sign.RsaSignedResponse;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class SmartActionDispatcherTest {
    
    @BeforeClass
    public static void init() throws Exception {
        MessageFactory.getInstance().init(Arrays.asList("com.cwa.msg"));
        ActionFactory.getInstance().init(Arrays.asList("com.cwa.msg"));
        
        RsaKeyHolder.getInstance().loadKeyAndCert();
    }
    
    @Test
    public void dispatchRsaAction() {
        byte[] msgData = new byte[]{0x23, 0x24, 0, 6, 0, 100, 0, 0, 0, 7};
        byte[] secureData = RsaUtil.encrypt(msgData, RsaKeyHolder.getInstance().getCertificate());

        ByteArrayGameOutput out = new ByteArrayGameOutput();
        out.putBytes(secureData);

        GameInput in = new ByteArrayGameInput(out.toByteArray());
        RsaRequest rsaMsg = new RsaRequest();
        rsaMsg.decodeBody(in);
        assertEquals(null, rsaMsg.getPlainRequest());
        
        SmartActionDispatcher dispatcher = new SmartActionDispatcher();
        dispatcher.setSecretProvider(new MockSecretProvider());
        dispatcher.dispatchAction(new CatchResponseGameSession(), rsaMsg);
        assertEquals(7, ((TestMessage) rsaMsg.getPlainRequest()).getId());
    }
    
    @Test
    public void dispatchMacAction() {
        byte[] msgData = new byte[] {0x23, 0x24, 0,6, 0,100, 0, 0, 0, 7};
        byte[] msgMac = DigestUtil.md5(msgData);
        
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        out.putBytes(msgData);
        out.putBytes(msgMac);
        
        ByteArrayGameInput in = new ByteArrayGameInput(out.toByteArray());
        Md5Request macMsg = new Md5Request();
        macMsg.decodeBody(in);
        
        assertEquals(null, macMsg.getWrappedRequest());
        assertTrue(Arrays.equals(msgMac, macMsg.getMsgMac()));
        assertTrue(Arrays.equals(msgData, macMsg.getMsgData()));
        
        SmartActionDispatcher dispatcher = new SmartActionDispatcher();
        dispatcher.setSecretProvider(new MockSecretProvider());
        dispatcher.dispatchAction(new CatchResponseGameSession(), macMsg);
        assertEquals(7, ((TestMessage) macMsg.getWrappedRequest()).getId());
    }
    
    @Test
    public void dispatchMacAction_fail() {
        byte[] msgData = new byte[] {0x23, 0x24, 0,6, 0,100, 0, 0, 0, 7};
        byte[] msgMac = DigestUtil.md5(msgData, "123".getBytes());
        
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        out.putBytes(msgData);
        out.putBytes(msgMac);
        
        ByteArrayGameInput in = new ByteArrayGameInput(out.toByteArray());
        Md5Request macMsg = new Md5Request();
        macMsg.decodeBody(in);
        
        assertEquals(null, macMsg.getWrappedRequest());
        assertTrue(Arrays.equals(msgMac, macMsg.getMsgMac()));
        assertTrue(Arrays.equals(msgData, macMsg.getMsgData()));
        
        try {
            SmartActionDispatcher dispatcher = new SmartActionDispatcher();
            dispatcher.setSecretProvider(new MockSecretProvider());
            dispatcher.dispatchAction(new CatchResponseGameSession(), macMsg);
            fail("应该抛出异常！");
        } catch (Exception expected) {
            assertEquals("Message has been modified!", expected.getMessage());
        }
    }
    
    @Test
    public void dispatchSafeCommand() {
        SmartActionDispatcher dispatcher = new SmartActionDispatcher();
        dispatcher.setSecretProvider(new MockSecretProvider());
        dispatcher.setSafeCommands(Arrays.asList(new TestMessage().getCommandId()));
        dispatcher.dispatchAction(new CatchResponseGameSession(), new TestMessage());
    }
    
    @Test
    public void dispatchSafeCommand2() {
        SmartActionDispatcher dispatcher = new SmartActionDispatcher();
        dispatcher.setSecretProvider(new MockSecretProvider());
        dispatcher.setUnsafeCommands(Arrays.asList(-1));
        dispatcher.dispatchAction(new CatchResponseGameSession(), new TestMessage());
    }
    
    @Test
    public void dispatchUnsafeAction() {
        try {
            SmartActionDispatcher dispatcher = new SmartActionDispatcher();
            dispatcher.setSecretProvider(new MockSecretProvider());
            dispatcher.dispatchAction(new CatchResponseGameSession(), new TestMessage());
            fail("应该抛出异常！");
        } catch (Exception expected) {
            assertEquals("Unsafe message", expected.getMessage().substring(0, 14));
        }
    }
    
    @Test
    public void signedResponse() {
        TestMessage resp = new TestMessage();
        resp.setId(369);
        
        RsaSignedResponse signedResp = new RsaSignedResponse(resp);
        
        byte[] bytes = MessageUtil.encode(signedResp);
        ByteArrayGameInput input = new ByteArrayGameInput(bytes);
        
        // header
        assertEquals(0x23, input.get());
        assertEquals(0x24, input.get());
        assertEquals(144, input.getShort());
        assertEquals(GameMessage.CMD_SIGNED_ACTION, input.getShort());
        
        // 验证签名
        byte[] respData = input.getBytes();
        byte[] respSign = input.getBytes();
        assertEquals(0, input.remaining());
        assertTrue(RsaUtil.verify(respData, respSign, RsaKeyHolder.getInstance().getCertificate()));
        
        TestMessage resp2 = (TestMessage) MessageUtil.decode(respData);
        assertEquals(resp.getId(), resp2.getId());
    }
    
    @Test
    public void dispatchAction_signResponse() {
        SmartActionDispatcher dispatcher = new SmartActionDispatcher();
        dispatcher.setSecretProvider(new MockSecretProvider());
        dispatcher.setUnsafeCommands(Arrays.asList(-1));
        dispatcher.setResponsesNeedToSign(Arrays.asList(new TestMessage().getCommandId()));
        
        CatchResponseGameSession session = new CatchResponseGameSession();
        TestMessage req = new TestMessage();
        req.setId(123456);
        dispatcher.dispatchAction(session, req);
        
        assertNotNull(session.getResponse());
        RsaSignedResponse signedResp = (RsaSignedResponse) session.getResponse();
        TestMessage resp = (TestMessage) signedResp.getWrappedResponse();
        assertEquals(req.getId(), resp.getId());
    }
    
    @Test
    public void dispatchBatchAction() {
        SmartActionDispatcher dispatcher = new SmartActionDispatcher();
        dispatcher.setUnsafeCommands(Arrays.asList(-1));
        BatchRequest req = new BatchRequest();
        req.setRequests(new GameRequest[] {
            new TestMessage(),
            new TestMessage(),
            new TestMessage()
        });
        
        CatchResponseGameSession session = new CatchResponseGameSession();
        dispatcher.dispatchAction(session, req);
        BatchResponse resp = (BatchResponse) session.getResponse();
        assertEquals(3, resp.getResponses().length);
    }
    
    @Test
    public void dispatchGzipAction() {
        SmartActionDispatcher dispatcher = new SmartActionDispatcher();
        dispatcher.setUnsafeCommands(Arrays.asList(-1));
        GzipRequest req = new GzipRequest() {
            @Override public GameRequest getWrappedRequest() {
                TestMessage req = new TestMessage();
                req.setId(987);
                return req;
            }
        };
        
        CatchResponseGameSession session = new CatchResponseGameSession();
        dispatcher.dispatchAction(session, req);
        TestMessage resp = (TestMessage) session.getResponse();
        assertEquals(987, resp.getId());
    }
    
}
