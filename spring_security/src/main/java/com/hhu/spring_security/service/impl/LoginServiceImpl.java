package com.hhu.spring_security.service.impl;

import com.hhu.spring_security.common.ResponseResult;
import com.hhu.spring_security.entity.LoginUser;
import com.hhu.spring_security.entity.SysUser;
import com.hhu.spring_security.service.LoginService;
import com.hhu.spring_security.utils.JwtUtil;
import com.hhu.spring_security.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;


    @Override
    public ResponseResult login(SysUser sysUser) {

        //1.调用AuthenticationManager的 authenticate方法,进行用户认证。
        // ctrl+h查看类的层次结构
        Authentication usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(sysUser.getUserName(),sysUser.getPassword());

        Authentication authentication =
                authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //2.如果认证没有通过,给出错误提示
        if(Objects.isNull(authentication)){
            throw new RuntimeException("登陆失败");
        }

        //3.如果认证通过,使用userId生成一个JWT,并将其保存到 ResponseResult对象中返回
            //3.1 获取经过身份验证的用户的主题信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            //3.2 获取userID 生成JWT
        String userId = loginUser.getSysUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //4.将用户信息存储在Redis中，在下一次请求时能够识别出用户,userid作为key
        redisCache.setCacheObject("login:"+userId,loginUser);
        //5.封装ResponseResult并返回
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult(200,"登陆成功",map);
    }
}
