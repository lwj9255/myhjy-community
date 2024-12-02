package com.hhu.myhjycommunity.system.service.Impl;

import com.github.pagehelper.util.StringUtil;
import com.hhu.myhjycommunity.system.mapper.SysMenuMapper;
import com.hhu.myhjycommunity.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> menuList = sysMenuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for(String menu : menuList){
            if(!StringUtil.isEmpty(menu)){
                permsSet.add(menu);
            }
        }
        return  permsSet;
    }
}
