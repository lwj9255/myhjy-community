package com.hhu.spring_security2.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CheckCode implements Serializable {

    private String code; // 验证字符
    private LocalDateTime expireTime; // 过期时间

    public CheckCode(String code, int expireTime) {
        this.code = code;
        // 返回指定的过期时间
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    public CheckCode(String code) {
        // 默认验证码 60秒后过期
        this(code, 60);
    }

    // 是否过期
    public boolean isExpired() {
        return this.expireTime.isBefore(LocalDateTime.now());
    }

    public String getCode() {
        return code;
    }
}