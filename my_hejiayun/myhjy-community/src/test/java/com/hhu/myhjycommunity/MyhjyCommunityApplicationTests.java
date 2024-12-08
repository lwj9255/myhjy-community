package com.hhu.myhjycommunity;

import com.hhu.myhjycommunity.system.domain.SysMenu;
import com.hhu.myhjycommunity.system.domain.SysUser;
import com.hhu.myhjycommunity.system.mapper.SysMenuMapper;
import com.hhu.myhjycommunity.system.mapper.SysUserMapper;
import com.hhu.myhjycommunity.system.service.SysMenuService;
import com.hhu.myhjycommunity.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
class MyhjyCommunityApplicationTests {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Test
    public void tsetSysUserMapper() {
        SysUser sysUser = sysUserMapper.selectUserByUserName("admin");
        System.out.println(sysUser);
    }

    @Test
    public void testSelectRoleAndMenuByUserId(){
        Set<String> role = sysRoleService.selectRolePermissionByUserId(1L);
        System.out.println("用户角色权限信息"+ role);

        Set<String> menu = sysMenuService.selectMenuPermsByUserId(2L);
        System.out.println("用户菜单权限信息"+ menu);
    }

    @Test
    public void testSelectMenuTree(){
//        System.out.println(sysMenuMapper.selectMenuTreeAll());
        List<SysMenu> sysMenus = sysMenuService.selectMenuTreeByUserId(2L);
        System.out.println(sysMenus);
    }
}
