package com.cwa.httpserver.action;

import com.cwa.gamecore.GameException;
import com.cwa.httpserver.message.HttpResponse;
import com.cwa.httpserver.message.SimpleResponse;
import com.cwa.httpserver.message.TestHttpRequest;
import com.cwa.httpserver.message.TestHttpResponse;
import com.cwa.httpserver.session.HttpGameSession;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;

public class HttpActionTest {
    
    @Test
    public void executeFail_gameException() {
        HttpGameSession session = new HttpGameSession();
        TestHttpRequest req = new TestHttpRequest();
        new HttpAction<TestHttpRequest, TestHttpResponse>() {
            @Override
            public HttpResponse execute(TestHttpRequest req) {
                throw new GameException(123, "Expected game exception!");
            }
        }.execute(session, req);
        
        SimpleResponse resp = (SimpleResponse) session.getResponse();
        assertThat(resp.getCommandId(), is(req.getCommandId()));
        assertThat(resp.getResponseCode(), is(123));
        assertThat(resp.getErrorMsg(), is("Expected game exception!"));
    }
    
    @Test
    public void executeFail_unknownException() {
        HttpGameSession session = new HttpGameSession();
        TestHttpRequest req = new TestHttpRequest();
        new HttpAction<TestHttpRequest, TestHttpResponse>() {
            @Override
            public HttpResponse execute(TestHttpRequest req) {
                throw new RuntimeException("Expected unknown exception!");
            }
        }.execute(session, req);
        
        SimpleResponse resp = (SimpleResponse) session.getResponse();
        assertThat(resp.getCommandId(), is(req.getCommandId()));
        assertThat(resp.getResponseCode(), is(HttpResponse.RC_UNKNOWN_ERROR));
        assertThat(resp.getErrorMsg(), is("Expected unknown exception!"));
    }
    
    @Test
    public void executeFail_checkRequestFail() {
        HttpGameSession session = new HttpGameSession();
        TestHttpRequest req = new TestHttpRequest();
        new HttpAction<TestHttpRequest, TestHttpResponse>() {
            @Override
            public void checkRequest(TestHttpRequest req) throws GameException {
                throw new GameException(9987, "Expected GameException: 9987");
            }
            
            @Override
            public HttpResponse execute(TestHttpRequest req) {
                fail("Should not go here!");
                return new TestHttpResponse();
            }
        }.execute(session, req);
        
        SimpleResponse resp = (SimpleResponse) session.getResponse();
        assertThat(resp.getCommandId(), is(req.getCommandId()));
        assertThat(resp.getResponseCode(), is(9987));
    }
    
}
