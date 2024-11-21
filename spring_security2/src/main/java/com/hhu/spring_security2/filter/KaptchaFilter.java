package com.hhu.spring_security2.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.code.kaptcha.Constants;
import com.hhu.spring_security2.entity.CheckCode;
import com.hhu.spring_security2.exception.KaptchNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class KaptchaFilter extends OncePerRequestFilter {

    // 前端输入的图形验证码参数
    private String codeParameter = "imageCode";

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //非POSR请求的表单提交不校验
        if (request.getMethod().equals("POST")){
            try {
                validate(request);
            } catch (KaptchNotMatchException e) {
                failureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }

        filterChain.doFilter(request,response);
    }

    private void validate(HttpServletRequest request) {
        //1.获取用户传入的图形验证码值
        String requestCode = request.getParameter(this.codeParameter);
        if(requestCode == null){
            requestCode="";
        }

        //2.获取session中保存的验证码正确值
        HttpSession session = request.getSession();
        CheckCode checkCode = (CheckCode)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

        if(checkCode!=null){
            // 不论验证是否成功，都要清除验证码，登陆失败后也要重新刷新的
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        }

        // 校验出错，抛出异常
        //验证输入的验证码是否为空
        if(StringUtils.isBlank(requestCode)){
            throw new KaptchNotMatchException("验证码的值不能为空");
        }
        //验证输入的验证码是否正确
        if(!requestCode.equalsIgnoreCase(checkCode.getCode())){
            throw new KaptchNotMatchException("验证码输入错误");
        }
        //验证输入的验证码是否过期
        if(checkCode.isExpired()){
            throw new KaptchNotMatchException("验证码已过期");
        }

    }
}
