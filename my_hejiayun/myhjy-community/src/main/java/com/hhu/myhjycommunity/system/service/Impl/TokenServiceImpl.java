package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.common.constant.Constants;
import com.hhu.myhjycommunity.common.utils.UUIDUtils;
import com.hhu.myhjycommunity.system.domain.LoginUser;
import com.hhu.myhjycommunity.system.service.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenServiceImpl implements TokenService {
    //令牌自定义表示
    @Value("${token.header}")
    private String header;

    //令牌秘钥
    @Value("${token.secret}")
    private String secret;

    //令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private String expireTime;


    /**
     * 创建令牌
     * @param loginUser
     * @return
     */
    @Override
    public String createToken(LoginUser loginUser) {
        String userkey = UUIDUtils.randomUUID();
        loginUser.setToken(userkey);

        Map<String,Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY,userkey);

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret).compact();

        return token;
    }
}
