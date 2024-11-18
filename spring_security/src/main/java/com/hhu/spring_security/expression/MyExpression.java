package com.hhu.spring_security.expression;

import com.hhu.spring_security.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 接口指定的访问权限限制
 */
@Component("my_ex")
public class MyExpression {
    public boolean hasAuthority(String authority) {
        //获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        //判断集合中是否有authority
        return permissions.contains(authority);
    }
}
