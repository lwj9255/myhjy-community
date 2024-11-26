package com.hhu.myhjycommunity.common.core.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaNotMatchException extends CustomException {
    public CaptchaNotMatchException() {
        super(400,"验证码错误");
    }
}
