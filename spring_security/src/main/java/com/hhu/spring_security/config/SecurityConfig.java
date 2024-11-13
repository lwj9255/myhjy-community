package com.hhu.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 把BCryptPasswordEncoder注入到spring容器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入AuthenticationManager,供外部类使用
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 用于配置HTTP请求的安全处理
     * @param
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               //关闭csrf
               .csrf().disable()
               //不创建会话，每个请求都视为独立的请求，STATELESS表示无状态，因为我们要使用jwt
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               // 定义请求授权规则
               .authorizeRequests()
               // 对于登录接口，允许匿名访问
               .antMatchers(("/sysUser/login")).anonymous()
               // 除了上面的接口，其他全都要鉴权认证
               .anyRequest().authenticated();
    }
}
