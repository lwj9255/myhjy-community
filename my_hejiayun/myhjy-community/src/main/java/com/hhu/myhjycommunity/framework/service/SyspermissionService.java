package com.hhu.myhjycommunity.framework.service;

import com.hhu.myhjycommunity.system.domain.SysUser;
import com.hhu.myhjycommunity.system.service.SysMenuService;
import com.hhu.myhjycommunity.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SyspermissionService {
    @Autowired
    private SysRoleService sysroleService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取角色数据权限
     */
    public Set<String> getRolePermission(SysUser sysUser){
        Set<String> roles = new HashSet<>();

        if(sysUser.isAdmin()){
            roles.add("admin");
        }else{
            roles = sysroleService.selectRolePermissionByUserId(sysUser.getUserId());
        }
        return  roles;
    }

    /**
     * 获取菜单数据权限
     */
    public Set<String> getMenuPermission(SysUser sysUser){
        Set<String> perms = new HashSet<>();

        if(sysUser.isAdmin()){
            perms.add("*:*:*");
        }else {
            perms = sysMenuService.selectMenuPermsByUserId(sysUser.getUserId());
        }
        return  perms;
    }
}
