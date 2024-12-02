package com.hhu.myhjycommunity.system.service;

import java.util.Set;

public interface SysRoleService {
    public Set<String> selectRolePermissionByUserId(Long userId);
}
