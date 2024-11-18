package com.hhu.spring_security.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class LoginUser implements UserDetails {

    private SysUser sysUser;

    private List<String> permissions;

    private List<SimpleGrantedAuthority> authorities;

    private List<String> roles;

    public LoginUser() {
    }

    public LoginUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public LoginUser(SysUser sysUser, List<String> permissions) {
        this.sysUser = sysUser;
        this.permissions = permissions;
    }

    public LoginUser(SysUser sysUser, List<String> perms, List<String> roles) {
        this.sysUser = sysUser;
        this.permissions = permissions;
        this.roles = roles;
    }

    /**
     * 用于获取用户被授予的权限，可以用于实现访问控制。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // //将permissions集合中的String类型权限信息,转换为SimpleGrantedAuthority类型
        if (authorities == null) {
            authorities =
                    permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            // SimpleGrantedAuthority::new 是方法引用的写法，相当于 permission -> new SimpleGrantedAuthority(permission)
            authorities =
                    roles.stream().map(role->new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toList());
        }
        return authorities;
    }

    /**
     * 用于获取用户的密码，一般用于进行密码验证。
     */
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    /**
     * 用于获取用户的用户名，一般用于进行身份验证。
     */
    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }

    /**
     * 用于判断用户的账户是否未过期，可以用于实现账户有效期控制。
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用于判断用户的账户是否未锁定，可以用于实现账户锁定功能。
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用于判断用户的凭证（如密码）是否未过期，可以用于实现密码有效期控制。
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用于判断用户是否已激活，可以用于实现账户激活功能。
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
