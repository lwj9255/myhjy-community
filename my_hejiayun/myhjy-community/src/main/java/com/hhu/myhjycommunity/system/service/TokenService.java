package com.hhu.myhjycommunity.system.service;

import com.hhu.myhjycommunity.system.domain.LoginUser;

import javax.servlet.http.HttpServletRequest;


public interface TokenService {
    /**
     * 创建令牌
     */
    public String createToken(LoginUser loginUser);

    /**
     * 缓存用户信息，并刷新令牌的有效期
     */
    public void refreshToken(LoginUser loginUser);

    /**
     * 获取用户信息
     */
    LoginUser getLoginUser(HttpServletRequest request);

    /**
     * 验证令牌有效期，并自动刷新缓存
     */
    public void verifyToken(LoginUser loginUser);

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser);

    /**
     * 删除用户信息
     */
    public void delLoginUser(String token);

}
