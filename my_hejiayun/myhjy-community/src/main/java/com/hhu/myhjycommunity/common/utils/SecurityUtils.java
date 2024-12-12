package com.hhu.myhjycommunity.common.utils;

import com.hhu.myhjycommunity.common.constant.HttpStatus;
import com.hhu.myhjycommunity.common.core.exception.CustomException;
import com.hhu.myhjycommunity.system.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户信息相关操作工具类
 */
public class SecurityUtils {
    /**
     * 获取用户名
     * @return
     */
    public static String getUserName(){
        return getLoginUser().getUsername();
    }

    /**
     * 获取完整用户登录信息
     * @return
     */
    public static LoginUser getLoginUser(){
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED,"获取用户信息异常");
        }
    }

    /**
     * 获取Authentication
     * @return
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
