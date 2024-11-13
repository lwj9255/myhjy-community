package com.hhu.spring_security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hhu.spring_security.entity.LoginUser;
import com.hhu.spring_security.entity.SysUser;
import com.hhu.spring_security.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName,username);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);

        if(Objects.isNull(sysUser)){
            throw new RuntimeException("用户名或密码错误");
        }

        return new LoginUser(sysUser);
    }
}
