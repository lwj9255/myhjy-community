package com.hhu.spring_security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @RequestMapping("/hellouser")
    @PreAuthorize("hasAnyAuthority('system:user:list')") //Spring Security 提供的一个注解，用于在方法执行前进行权限校验
    // 检查当前登录用户是否具有指定的权限(这里是‘system:user:list')，如果有，则允许执行该方法，否则抛出 `AccessDeniedException` 异常，阻止方法执行。
    public String helloUser(){
        System.out.println("hello user");
        return "hello user";
    }

    @RequestMapping("/hellorole")
    @PreAuthorize("hasAnyAuthority('system:role:list')") //Spring Security 提供的一个注解，用于在方法执行前进行权限校验
    // 检查当前登录用户是否具有指定的权限(这里是‘system:role:list')，如果有，则允许执行该方法，否则抛出 `AccessDeniedException` 异常，阻止方法执行。
    public String helloRole(){
        System.out.println("hello role");
        return "hello role";
    }

    @RequestMapping("/hellomenu")
//    @PreAuthorize("hasAnyAuthority('system:menu:list')") //Spring Security 提供的一个注解，用于在方法执行前进行权限校验
    // 检查当前登录用户是否具有指定的权限(这里是‘system:role:list')，如果有，则允许执行该方法，否则抛出 `AccessDeniedException` 异常，阻止方法执行。
    public String helloMenu(){
        System.out.println("hello role");
        return "hello role";
    }

    @RequestMapping("/helloMyEx")
    @PreAuthorize("@my_ex.hasAuthority('system:user:list')")
    public String helloMyEx(){
        System.out.println("hello myex");
        return "hello myex";
    }

    @RequestMapping("/level1")
    @PreAuthorize("hasAnyAuthority('system:user:list','system:role:list') AND hasRole('common')")
    public String hellolevel1(){
        System.out.println("hello level1");
        return "hello level1";
    }

}
