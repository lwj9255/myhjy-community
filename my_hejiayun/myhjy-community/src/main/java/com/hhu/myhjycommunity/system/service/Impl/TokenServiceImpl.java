package com.hhu.myhjycommunity.system.service.Impl;

import com.github.pagehelper.util.StringUtil;
import com.hhu.myhjycommunity.common.constant.Constants;
import com.hhu.myhjycommunity.common.utils.RedisCache;
import com.hhu.myhjycommunity.common.utils.UUIDUtils;
import com.hhu.myhjycommunity.system.domain.LoginUser;
import com.hhu.myhjycommunity.system.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisCache redisCache;

    //令牌自定义表示
    @Value("${token.header}")
    private String header;

    //令牌秘钥
    @Value("${token.secret}")
    private String secret;

    //令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    // 毫秒
    private static final int MILLIS_SECOND = 1000;
    // 分钟
    private static final int MILLIS_MINUTE = 60 * MILLIS_SECOND;
    // 二十分钟
    private static final int MILLIS_MINUTE_TEN = 20 * MILLIS_MINUTE;



    /**
     * 创建令牌
     */
    @Override
    public String createToken(LoginUser loginUser) {

        // 设置用户的唯一标识
        String userkey = UUIDUtils.randomUUID();
        loginUser.setToken(userkey);

        // 保存用户信息，刷新令牌
        refreshToken(loginUser);

        HashMap<String,Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY,userkey);

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret).compact();

        return token;
    }

    @Override
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        // 过期时间30分钟
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据UUID将loginuser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey,loginUser,expireTime, TimeUnit.MINUTES);
    }

    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的token
        String token = getToken(request);
        if(!StringUtil.isEmpty(token)){
            Claims claims = parseToken(token);
            // 解析对应的用户信息和权限信息
            String uuid = (String)claims.get(Constants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            LoginUser loginUser = redisCache.getCacheObject(userKey);
            return loginUser;
        }
        return null;
    }

    @Override
    public void verifyToken(LoginUser loginUser) {
        Long expireTime = loginUser.getExpireTime();
        long currentTimeMillis = System.currentTimeMillis();
        if(expireTime - currentTimeMillis <= MILLIS_MINUTE_TEN){
            refreshToken(loginUser);
        }
    }

    @Override
    public void setLoginUser(LoginUser loginUser) {
        if(!Objects.isNull(loginUser) && !StringUtil.isEmpty(loginUser.getToken())){
            refreshToken(loginUser);
        }
    }

    @Override
    public void delLoginUser(String token) {
        if(!StringUtil.isEmpty(token)){
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 拼接登录的唯一标识
     */
    private String getTokenKey(String uuid){
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 从request中获取token
     */
    private String getToken(HttpServletRequest request){
        String token = request.getHeader(this.header);
        // `jwt token`的标准写法 `Authorization: Bearer aaa.bbb.ccc`。 (bearer: 持票人,也就是TOKEN_PREFIX中规定的字符串)
        if(!StringUtil.isEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)){
            token = token.replace(Constants.TOKEN_PREFIX,"");
        }
        return token;
    }

    private Claims parseToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
