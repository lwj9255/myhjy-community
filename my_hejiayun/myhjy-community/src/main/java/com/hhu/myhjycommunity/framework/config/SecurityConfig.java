package com.hhu.myhjycommunity.framework.config;

import com.hhu.myhjycommunity.framework.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true) // Spring Security 提供的一个注解，用于启用全局方法级别的安全控制
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationEntryPoint unathorizedHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private CorsFilter corsFilter;

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
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //不需要使用session，所以关闭csrf
        http.csrf().disable();


        //不创建会话，每个请求都视为独立的请求，STATELESS表示无状态，因为我们要使用jwt
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 定义请求授权规则
        http.authorizeRequests()
               // 对于登录接口，允许匿名访问
               .mvcMatchers("/captchaImage","/login").anonymous()
                //配置形式的权限控制
                //.antMatchers().hasAuthority()
               // 除了上面的接口，其他全都要鉴权认证
               .anyRequest().authenticated();


        // 配置认证失败处理器
        http.exceptionHandling().authenticationEntryPoint(unathorizedHandler);

        // 配置登出处理器
        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

        // 添加JWTFilter
        http.addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);

        // 添加CORS Filter
        http.addFilterBefore(corsFilter,JwtAuthenticationTokenFilter.class);
        //确保在用户注销登录时，响应头中包含必要的跨域资源共享（CORS）字段
        http.addFilterBefore(corsFilter, LogoutFilter.class);
    }
}
