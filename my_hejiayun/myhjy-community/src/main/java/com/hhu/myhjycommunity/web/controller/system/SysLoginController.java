package com.hhu.myhjycommunity.web.controller.system;

import com.hhu.myhjycommunity.common.utils.ChainedMap;
import com.hhu.myhjycommunity.system.domain.vo.LoginBody;
import com.hhu.myhjycommunity.system.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/login")
    public ChainedMap login(@RequestBody LoginBody loginBody){
        String token =
                sysLoginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        return ChainedMap.create().set("token",token);
    }
}
