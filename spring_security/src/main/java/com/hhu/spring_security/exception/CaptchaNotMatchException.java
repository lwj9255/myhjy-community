package com.hhu.spring_security.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaNotMatchException extends AuthenticationException {
    public CaptchaNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CaptchaNotMatchException(String msg) {
        super(msg);
    }
}
