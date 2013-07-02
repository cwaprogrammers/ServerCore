package com.cwa.httpserver.action;

import com.cwa.httpserver.message.HttpResponse;
import com.cwa.httpserver.message.TestHttpRequest;
import com.cwa.httpserver.message.TestHttpResponse;

public class TestHttpAction extends HttpAction<TestHttpRequest, TestHttpResponse> {

    @Override
    public HttpResponse execute(TestHttpRequest req) {
        return new TestHttpResponse();
    }

}
