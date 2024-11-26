package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.common.constant.Constants;
import com.hhu.myhjycommunity.common.core.exception.BaseException;
import com.hhu.myhjycommunity.common.core.exception.UserPasswordNotMatchException;
import com.hhu.myhjycommunity.common.utils.RedisCache;
import com.hhu.myhjycommunity.common.core.exception.CaptchaNotMatchException;
import com.hhu.myhjycommunity.system.domain.LoginUser;
import com.hhu.myhjycommunity.system.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SysLoginServiceImpl implements SysLoginService {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServiceImpl tokenService;

    /**
     * 带验证码登录
     */
    @Override
    public String login(String username, String password, String code, String uuid) {
        //1.从Redis中获取验证码，判断是否正确
        String verfiykey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verfiykey);
        redisCache.deleteObject(verfiykey);

        if(captcha == null || !code.equalsIgnoreCase(captcha)){ // equalsIgnoreCase忽略大小写考虑的比较字符
            throw new CaptchaNotMatchException();
        }

        //2.用户认证
        Authentication authenticate = null;
        try {
            authenticate =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
           throw new UserPasswordNotMatchException();
        }

        //3.获取通过身份验证的用户的主体信息
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();

        //4.调用TokenService 生成token
        return tokenService.createToken(loginUser);
    }
}
