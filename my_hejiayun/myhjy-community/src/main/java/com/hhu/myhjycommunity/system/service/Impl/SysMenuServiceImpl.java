package com.hhu.myhjycommunity.system.service.Impl;

import com.github.pagehelper.util.StringUtil;
import com.hhu.myhjycommunity.common.constant.UserConstants;
import com.hhu.myhjycommunity.common.core.domain.TreeSelect;
import com.hhu.myhjycommunity.system.domain.SysMenu;
import com.hhu.myhjycommunity.system.domain.SysRole;
import com.hhu.myhjycommunity.system.domain.SysUser;
import com.hhu.myhjycommunity.system.domain.vo.MetaVo;
import com.hhu.myhjycommunity.system.domain.vo.RouterVo;
import com.hhu.myhjycommunity.system.mapper.SysMenuMapper;
import com.hhu.myhjycommunity.system.mapper.SysRoleMapper;
import com.hhu.myhjycommunity.system.mapper.SysRoleMenuMapper;
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

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;



    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> menuList = sysMenuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String menu : menuList) {
            if(!StringUtils.isEmpty(menu)){
                permsSet.add(menu);
            }
        }
        return permsSet;
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
            routerVo.setName(getRouteName(menu));
            routerVo.setPath(getRoutePath(menu));
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
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(Long userId) {
        return selectMenuList(new SysMenu(), userId);
    }


    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenu> menuList = null;
        // 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId))
        {
            menuList = sysMenuMapper.selectMenuList(menu);
        }
        else
        {
            menu.getParams().put("userId", userId);
            menuList = sysMenuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Integer> selectMenuListByRoleId(Long roleId) {
        SysRole role = sysRoleMapper.selectRoleById(roleId);
        return sysMenuMapper.selectMenuListByRoleId(roleId, role.isMenuCheckStrictly());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        return null;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
        return null;
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(Long menuId) {
        return sysMenuMapper.selectMenuById(menuId);
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean hasChildByMenuId(Long menuId) {
        int result = sysMenuMapper.hasChildByMenuId(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean checkMenuExistRole(Long menuId) {
        int result = sysRoleMenuMapper.checkMenuExistRole(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenu menu) {
        return sysMenuMapper.insertMenu(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        return sysMenuMapper.updateMenu(menu);
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId) {
        return sysMenuMapper.deleteMenuById(menuId);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        Long menuId = Objects.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        SysMenu info = sysMenuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (!Objects.isNull(info) && info.getMenuId().longValue() != menuId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
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
    private String getRoutePath(SysMenu menu) {
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
    private String getRouteName(SysMenu menu) {
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
