package com.cwa.httpserver.message;

import com.cwa.gamecore.io.ByteArrayGameInput;
import com.cwa.gamecore.io.ByteArrayGameOutput;
import org.junit.Assert;
import org.junit.Test;

public class HttpMessageTest {
    
    @Test
    public void encodeRequest() {
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        out.putInt(12345);
        out.putString("spiderman");
        
        TestHttpRequest req = new TestHttpRequest();
        ByteArrayGameInput in = new ByteArrayGameInput(out.toByteArray());
        req.decodeBody(in);
        
        Assert.assertEquals(0, in.remaining());
        Assert.assertEquals(12345, req.getUserId());
        Assert.assertEquals("spiderman", req.getUsername());
    }
    
    @Test
    public void decodeResponse_responseCode_NotOK() {
        TestHttpResponse resp = new TestHttpResponse();
        resp.setResponseCode(1);
        resp.setErrorMsg("Big problem!");
        resp.setMessage("I will be back!");
        
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        resp.encodeBody(out);
        
        ByteArrayGameInput in = new ByteArrayGameInput(out.toByteArray());
        
        Assert.assertEquals(1, in.getShort());
        Assert.assertEquals("Big problem!", in.getString());
        Assert.assertEquals(0, in.remaining());
    }
    
    @Test
    public void decodeResponse_responseCodeOK() {
        TestHttpResponse resp = new TestHttpResponse();
        //resp.setResponseCode(1);
        resp.setMessage("I will be back!");
        
        ByteArrayGameOutput out = new ByteArrayGameOutput();
        resp.encodeBody(out);
        
        ByteArrayGameInput in = new ByteArrayGameInput(out.toByteArray());
        
        Assert.assertEquals(0, in.getShort());
        Assert.assertEquals("I will be back!", in.getString());
        Assert.assertEquals(0, in.remaining());
    }
    
}
