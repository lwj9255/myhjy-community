package com.hhu.spring_security2.exception;


import org.springframework.security.core.AuthenticationException;

public class KaptchNotMatchException extends AuthenticationException {

    public KaptchNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public KaptchNotMatchException(String msg) {
        super(msg);
    }
}
