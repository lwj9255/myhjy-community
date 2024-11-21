package com.hhu.spring_security.service;

import com.hhu.spring_security.common.ResponseResult;
import com.hhu.spring_security.entity.SysUser;

public interface LoginService {
    ResponseResult login(SysUser sysUser);

    ResponseResult logout();

    String login(String userName, String password, String code, String uuid);
}
