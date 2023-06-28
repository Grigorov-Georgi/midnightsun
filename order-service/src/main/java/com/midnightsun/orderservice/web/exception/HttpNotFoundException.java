package com.midnightsun.orderservice.web.exception;


import com.midnightsun.orderservice.web.exception.enums.ErrorCode;

public class HttpNotFoundException extends DebuggableException {
    public HttpNotFoundException(String debugMessage) {
        super(ErrorCode.NOT_FOUND, debugMessage);
    }

    public HttpNotFoundException(String debugMessage, Throwable throwable) {
        super(ErrorCode.NOT_FOUND, debugMessage, throwable);
    }
}
