package com.hhu.myhjycommunity.system.service.Impl;

import com.github.pagehelper.util.StringUtil;
import com.hhu.myhjycommunity.system.mapper.SysRoleMapper;
import com.hhu.myhjycommunity.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<String> roleList = sysRoleMapper.selectRolePermissionByUserId(userId);

        Set<String> permsSet = new HashSet<>();
        for(String rolekey : roleList){
            if(!StringUtil.isEmpty(rolekey)){
                permsSet.add(rolekey);
            }
        }
        return  permsSet;
    }
}
