package com.hhu.myhjycommunity.system.service.Impl;

import com.github.pagehelper.util.StringUtil;
import com.hhu.myhjycommunity.common.constant.UserConstants;
import com.hhu.myhjycommunity.system.domain.SysMenu;
import com.hhu.myhjycommunity.system.domain.vo.MetaVo;
import com.hhu.myhjycommunity.system.domain.vo.RouterVo;
import com.hhu.myhjycommunity.system.mapper.SysMenuMapper;
import com.hhu.myhjycommunity.system.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for(SysMenu menu : menus){
            RouterVo routerVo = new RouterVo();
            routerVo.setName(getRouterName(menu));
            routerVo.setPath(getRouterPath(menu));
            routerVo.setComponent(getComponent(menu));
            routerVo.setHidden("1".equals(menu.getVisible()));
            routerVo.setMeta(new MetaVo(menu.getMenuName(),menu.getIcon(),"1".equals(menu.getIsCache())));
            List<SysMenu> subMenus = menu.getChildren();
            if(!subMenus.isEmpty() && subMenus.size()>0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())){
                routerVo.setAlwaysShow(true);
                routerVo.setRedirect("noRedirect");
                routerVo.setChildren(buildMenus(subMenus));
            }
            routers.add(routerVo);
        }
        return routers;
    }

    /**
     *获取组件信息
     */
    private String getComponent(SysMenu menu) {
        // 如果组件没信息，则用LAYOUT
        String component = UserConstants.LAYOUT;
        // 如果组件有信息，则用信息
        if(!StringUtils.isEmpty(menu.getComponent())){
            component = menu.getComponent();
        // 如果不是一级目录，则用PARENT_VIEW
        }else if(menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())){
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     *获取路由地址
     */
    private String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();

        // 如果是一级目录，则加在路由地址前加一个斜杠
        if(0 == menu.getParentId().intValue()
                && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame())){
            routerPath = "/" + menu.getPath();
        }

        return routerPath;
    }

    /**
     * 获取路由名称
     */
    private String getRouterName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        return routerName;
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
