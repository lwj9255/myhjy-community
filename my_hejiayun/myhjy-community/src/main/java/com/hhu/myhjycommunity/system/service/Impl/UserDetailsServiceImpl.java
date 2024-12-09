package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.common.enums.UserStatus;
import com.hhu.myhjycommunity.framework.service.SysPermissionService;
import com.hhu.myhjycommunity.system.domain.LoginUser;
import com.hhu.myhjycommunity.system.domain.SysUser;
import com.hhu.myhjycommunity.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectUserByUserName(username);

        if(Objects.isNull(sysUser)){
            log.info("登录用户：{} 不存在",username);
            throw new UsernameNotFoundException("登录用户：" + username + "不存在");
        }
        else if(UserStatus.DELETES.getCode().equals(sysUser.getDelFlag())){
            log.info("登录用户：{} 已删除",username);
            throw new UsernameNotFoundException("登录用户：" + username + "已删除");
        }
        else if(UserStatus.DISABLE.getCode().equals(sysUser.getStatus())){
            log.info("登录用户：{} 已停用",username);
            throw new UsernameNotFoundException("登录用户：" + username + "已停用");
        }

        return createLoginUser(sysUser);
    }

    public UserDetails createLoginUser(SysUser sysUser){
        return new LoginUser(sysUser,permissionService.getMenuPermission(sysUser));
    }
}
