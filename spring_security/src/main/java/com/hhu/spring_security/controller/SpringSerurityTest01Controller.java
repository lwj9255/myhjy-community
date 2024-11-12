package com.hhu.spring_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSerurityTest01Controller {
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
