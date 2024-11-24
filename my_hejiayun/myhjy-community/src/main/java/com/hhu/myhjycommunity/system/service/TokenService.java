package com.hhu.myhjycommunity.system.service;

import com.hhu.myhjycommunity.system.domain.LoginUser;


public interface TokenService {
    /**
     * 创建令牌
     * @param loginUser
     * @return
     */
    public String createToken(LoginUser loginUser);
}
