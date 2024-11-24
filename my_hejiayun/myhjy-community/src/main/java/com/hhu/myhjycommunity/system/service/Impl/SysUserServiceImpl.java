package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.system.domain.SysUser;
import com.hhu.myhjycommunity.system.mapper.SysUserMapper;
import com.hhu.myhjycommunity.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }
}
