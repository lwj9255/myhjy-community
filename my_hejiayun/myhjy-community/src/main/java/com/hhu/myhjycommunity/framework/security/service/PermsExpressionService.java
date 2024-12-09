package com.hhu.myhjycommunity.framework.security.service;

import com.hhu.myhjycommunity.common.utils.ServletUtils;
import com.hhu.myhjycommunity.system.domain.LoginUser;
import com.hhu.myhjycommunity.system.domain.SysRole;
import com.hhu.myhjycommunity.system.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Set;

@Component("mype")
public class PermsExpressionService {
    public static final String ALL_PERMISSION = "*:*:*";

    @Autowired
    private TokenService tokenService;

    /**
     *验证用户是否具备某权限
     */
    public boolean hasPerms(String permission){
        // 如果传入的权限为空，则返回false
        if(StringUtils.isEmpty(permission)){
            return false;
        }

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        // 如果登录用户为空 或者 登录用户的权限为空，则返回false
        if(Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())){
            return false;
        }

        return hasPermissions(loginUser.getPermissions(),permission);
    }

    /**
     *判断是否包含权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission) {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(permission);
    }

    /**
     *验证用户是否具备以下任意一个权限
     */
    public boolean hasAnyPerms(String permissions){
        // 如果传入的权限为空，则返回false
        if(StringUtils.isEmpty(permissions)){
            return false;
        }

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        // 如果登录用户为空 或者 登录用户的权限为空，则返回false
        if(Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())){
            return false;
        }

        Set<String> authorities = loginUser.getPermissions();

        for(String permission : permissions.split(",")){
            if(permission != null && hasPermissions(authorities,permission)) {
                return true;
            }
        }

        return false;
    }

    /**
     *验证用户是否拥有某个角色
     */
    public boolean hasRole(String role){
        if(StringUtils.isEmpty(role)){
            return false;
        }

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        if(Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getSysUser().getRoles())){
            return false;
        }

        for(SysRole sysRole : loginUser.getSysUser().getRoles()){
            String roleKey = sysRole.getRoleKey();
            if("admin".equals(roleKey) || roleKey.contains(role)){
                return true;
            }
        }
        return false;
    }

    /**
     *验证用户是否拥有任意一个角色
     */
    public boolean hasAnyRole(String roles){
        if(StringUtils.isEmpty(roles)){
            return false;
        }

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        if(Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getSysUser().getRoles())){
            return false;
        }

        for(String role : roles.split(",")){
            if(hasRole(role)){
                return true;
            }
        }
        return false;
    }

}
