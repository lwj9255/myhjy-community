package com.hhu.spring_security.controller;

import com.hhu.spring_security.common.ResponseResult;
import com.hhu.spring_security.entity.LoginBody;
import com.hhu.spring_security.entity.SysUser;
import com.hhu.spring_security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/sysUser/login")
    public ResponseResult login(@RequestBody SysUser sysUser){
        return loginService.login(sysUser);
    }

    @GetMapping("/sysUser/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

    @PostMapping("/loginbody/captchalogin")
    public ResponseResult login(@RequestBody LoginBody loginBody){
        String token = loginService.login(loginBody.getUserName(),loginBody.getPassword()
        ,loginBody.getCode(),loginBody.getUuid());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return new ResponseResult<>(200,"登陆成功",map);
    }
}
