package com.hhu.myhjycommunity.common.core.exception;

public class UserPasswordNotMatchException extends CustomException{
    public UserPasswordNotMatchException() {
        super(401,"用户不存在/密码错误");
    }
}
