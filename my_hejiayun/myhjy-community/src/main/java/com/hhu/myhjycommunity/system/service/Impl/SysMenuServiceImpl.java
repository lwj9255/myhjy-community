package com.hhu.myhjycommunity.system.service.Impl;

import com.github.pagehelper.util.StringUtil;
import com.hhu.myhjycommunity.system.domain.SysMenu;
import com.hhu.myhjycommunity.system.mapper.SysMenuMapper;
import com.hhu.myhjycommunity.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        if(userId != null && userId == 1L){
            menus = sysMenuMapper.selectMenuTreeAll();
        }else{
            menus = sysMenuMapper.selectMenuTreeByUserId(userId);
        }

        return getChildPerms(menus,0);
    }

    /**
     * 根据父节点ID获取所有子节点
     * @param menus 菜单列表
     * @param parentId 父节点ID
     * @return
     */
    private List<SysMenu> getChildPerms(List<SysMenu> menus, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        menus.stream()
                .filter(m -> m.getParentId() == parentId)
                .forEach(m -> {
                    recursionFn(menus,m);
                    returnList.add(m);
                });
        return returnList;
    }

    /**
     * 递归获取子菜单
     * @param menus 菜单列表
     * @param m 子节点
     */
    private void recursionFn(List<SysMenu> menus, SysMenu m) {
        List<SysMenu> childList = getChildList(menus, m);
        m.setChildren(childList);
        for(SysMenu child : childList){
            // 判断该节点下是否还有子节点
            if(getChildList(menus,child).size()>0){
                recursionFn(menus,child);
            }
        }
    }

    /**
     * 获取子节点列表
     * @param menus 菜单列表
     * @param m  子节点
     */
    private List<SysMenu> getChildList(List<SysMenu> menus, SysMenu m) {
        List<SysMenu> subMenus = menus.stream()
                // 对比菜单列表中的父节点和m的ID是否一致，如果一直则说明该节点是m的子节点
                .filter(sub -> sub.getParentId().longValue() == m.getMenuId().longValue())
                .collect(Collectors.toList());

        return subMenus;
    }
}
