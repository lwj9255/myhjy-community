package com.hhu.myhjycommunity.system.service;

import com.hhu.myhjycommunity.system.domain.SysMenu;
import com.hhu.myhjycommunity.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

public interface SysMenuService {
    public Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单树信息
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    public List<RouterVo> buildMenus(List<SysMenu> menus);
}
