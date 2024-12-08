package com.hhu.myhjycommunity.web.controller.system;

import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.utils.ChainedMap;
import com.hhu.myhjycommunity.common.utils.ServletUtils;
import com.hhu.myhjycommunity.framework.service.SyspermissionService;
import com.hhu.myhjycommunity.system.domain.LoginUser;
import com.hhu.myhjycommunity.system.domain.SysMenu;
import com.hhu.myhjycommunity.system.domain.SysUser;
import com.hhu.myhjycommunity.system.domain.vo.LoginBody;
import com.hhu.myhjycommunity.system.mapper.SysMenuMapper;
import com.hhu.myhjycommunity.system.service.SysLoginService;
import com.hhu.myhjycommunity.system.service.SysMenuService;
import com.hhu.myhjycommunity.system.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    private SyspermissionService syspermissionService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/login")
    public ChainedMap login(@RequestBody LoginBody loginBody){
        String token =
                sysLoginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        return ChainedMap.create().set("token",token);
    }

    @GetMapping("/getInfo")
    public ChainedMap getInfo(){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getSysUser();

        Set<String> rolePermission = syspermissionService.getRolePermission(sysUser);
        Set<String> menuPermission = syspermissionService.getMenuPermission(sysUser);

        ChainedMap chainedMap = ChainedMap.create().set("code", 200).set("msg", "操作成功");
        chainedMap.put("user",sysUser);
        chainedMap.put("roles",rolePermission);
        chainedMap.put("permissions",menuPermission);
        return chainedMap;
    }

    /**
     * 获取菜单路由信息
     */
    @GetMapping("/getRouters")
    public BaseResponse getRouters(){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getSysUser();
        List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(sysUser.getUserId());
        return BaseResponse.success(menus);
    }


}
