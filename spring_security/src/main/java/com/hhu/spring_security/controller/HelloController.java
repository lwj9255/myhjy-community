package com.hhu.spring_security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @RequestMapping("/hello")
    @PreAuthorize("hasAnyAuthority('test')")
    public String hello(){
        System.out.println("hello succeed");
        return "hello";
    }
}
