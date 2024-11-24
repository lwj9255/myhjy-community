package com.hhu.myhjycommunity.system.service;

import com.hhu.myhjycommunity.system.domain.SysUser;

public interface SysUserService {
    public SysUser selectUserByUserName(String userName);
}
