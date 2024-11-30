package com.hhu.myhjycommunity.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LoginUser implements UserDetails {
    // 用户信息
    private SysUser sysUser;

    // 用户唯一标识
    private String token;

    // 登录时间
    private Long loginTime;

    // 过期时间
    private Long expireTime;

    public LoginUser() {
    }

    public LoginUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }


    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     *  用于获取用户被授予的权限，可以用于实现访问控制。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 用于获取用户的密码，一般用于进行密码验证。
     */
    @JsonIgnore
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    /**
     * 用于获取用户的用户名，一般用于进行身份验证。
     */
    @JsonIgnore
    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }

    /**
     * 用于判断用户的账户是否未过期，可以用于实现账户有效期控制。
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用于判断用户的账户是否未锁定，可以用于实现账户锁定功能。
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用于判断用户的凭证（如密码）是否未过期，可以用于实现密码有效期控制。
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用于判断用户是否已激活，可以用于实现账户激活功能。
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
