package com.hhu.myhjycommunity.framework.security.hanlder;

import com.alibaba.fastjson.JSON;
import com.hhu.myhjycommunity.common.constant.HttpStatus;
import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        Integer code = HttpStatus.UNAUTHORIZED;
        ServletUtils.renderString(response,
                JSON.toJSONString(BaseResponse.fail(code.toString(),"认证失败，无法访问系统资源")));
    }
}
