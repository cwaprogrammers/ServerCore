package com.cwa.protobuf;

public class ProtobufException extends RuntimeException {

    public ProtobufException(String message) {
        super(message);
    }

    public ProtobufException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
