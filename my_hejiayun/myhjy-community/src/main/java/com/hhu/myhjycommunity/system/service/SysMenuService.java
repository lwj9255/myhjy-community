package com.hhu.myhjycommunity.system.service;

import java.util.Set;

public interface SysMenuService {
    public Set<String> selectMenuPermsByUserId(Long userId);
}
