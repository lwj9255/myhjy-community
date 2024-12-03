package com.hhu.myhjycommunity.web.controller.system;

import com.hhu.myhjycommunity.common.utils.ChainedMap;
import com.hhu.myhjycommunity.common.utils.ServletUtils;
import com.hhu.myhjycommunity.framework.service.SyspermissionService;
import com.hhu.myhjycommunity.system.domain.LoginUser;
import com.hhu.myhjycommunity.system.domain.SysUser;
import com.hhu.myhjycommunity.system.domain.vo.LoginBody;
import com.hhu.myhjycommunity.system.service.SysLoginService;
import com.hhu.myhjycommunity.system.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    private SyspermissionService syspermissionService;
    @Autowired
    private TokenService tokenService;

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


}
