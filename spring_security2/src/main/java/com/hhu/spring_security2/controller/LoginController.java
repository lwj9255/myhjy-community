package com.hhu.spring_security2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login.html")
    public String login(){
        return "login";
    }
    @RequestMapping("/index")
    public String success(){
        return "/index.html";
    }
}
