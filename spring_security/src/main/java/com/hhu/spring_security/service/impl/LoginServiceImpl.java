package com.hhu.spring_security.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.hhu.spring_security.common.Constants;
import com.hhu.spring_security.common.ResponseResult;
import com.hhu.spring_security.entity.LoginUser;
import com.hhu.spring_security.entity.SysUser;
import com.hhu.spring_security.exception.CaptchaNotMatchException;
import com.hhu.spring_security.service.LoginService;
import com.hhu.spring_security.utils.JwtUtil;
import com.hhu.spring_security.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        // 认证流程:
        // UsernamePasswordAuthenticationToken是实现了Authentication 接口的类,用于封装用户的身份信息
        // 认证前:
            // 当用户提交用户名和密码时创建此对象
            // 这时对象中的 principal（通常是用户名）和 credentials（通常是密码）可以进行身份验证
            // 但 authorities（权限）通常为空。
        // 认证后：
            // 认证成功后，authorities 会填充用户的权限
            // principal 会包含用户的详细信息
            // credentials 通常为空，因为密码已经不再需要。
        // 封装好UsernamePasswordAuthenticationToken后传入authenticate() 方法进行认证
        // authenticate() 方法会触发认证流程
        // 根据 AuthenticationManager 的配置来选择合适的 AuthenticationProvider 进行认证
        // 默认是DaoAuthenticationProvider
        // 然后DaoAuthenticationProvider 会调用 UserDetailsService
        // 自定义一个实现类UserDetailsServiceImpl来实现UserDetailsService
        // 重写其中的loadUserByUsername方法,在方法中编写认证的条件
        // 会去调用UserDetailsServiceImpl.loadUserByUsername
        // 在条件中写了是从sys_user这个表中根据名词取出项，如果取出的是空则登录失败
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


    /**
     * 登出
     * @return
     */
    @Override
    public ResponseResult logout() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authenticationToken)){
            throw new RuntimeException("获取用户认证信息失败，请重新登录！");
        }

        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        Long userId = loginUser.getSysUser().getUserId();

        //删除Redis中的用户信息
        redisCache.deleteObject("login:"+userId);


        return new ResponseResult(200,"注销成功");
    }

    /**
     * 带验证码登录
     * @param userName
     * @param password
     * @param code
     * @param uuid
     * @return
     */
    @Override
    public String login(String userName, String password, String code, String uuid) {
        // 从Redis中获取验证码
        String verifykey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifykey);
        redisCache.deleteObject(verifykey);

        if(captcha == null || !code.equalsIgnoreCase(captcha)){
            throw new CaptchaNotMatchException("验证码错误！");
        }

        Authentication usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userName,password);

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

        return jwt;
    }
}
