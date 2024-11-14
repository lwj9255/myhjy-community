package com.hhu.spring_security.filter;

import com.hhu.spring_security.entity.LoginUser;
import com.hhu.spring_security.utils.JwtUtil;
import com.hhu.spring_security.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义认证过滤器,用来校验用户请求中携带的Token
 * @author spikeCong
 * @date 2023/4/25
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    /**
     * 封装自定义过滤器的执行逻辑
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        //1.从请求头中获取token
        String token = request.getHeader("token");

        //2.判断token是否为空,为空直接放行
        if(!StringUtils.hasText(token)){
            //放行
            filterChain.doFilter(request, response);

            //return的作用是返回响应的时候,返回响应不带有token，就可以避免走下面的逻辑
            return;
        }

        //3.解析Token
        String sysUserId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            sysUserId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("非法token");
        }

        //4.从redis中获取用户信息
        String redisKey = "login:" + sysUserId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }

        //5.将用户信息保存到SecurityContextHolder,以便后续的访问控制和授权操作使用
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //6.放行
        filterChain.doFilter(request, response);
    }
}

