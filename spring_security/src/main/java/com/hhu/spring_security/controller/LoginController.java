package com.hhu.spring_security.controller;

import com.hhu.spring_security.common.ResponseResult;
import com.hhu.spring_security.entity.SysUser;
import com.hhu.spring_security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
