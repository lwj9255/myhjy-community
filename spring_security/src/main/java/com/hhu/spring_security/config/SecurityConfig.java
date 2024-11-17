package com.hhu.spring_security.config;

import com.hhu.spring_security.exception.AccessDeniedHandlerImpl;
import com.hhu.spring_security.exception.AuthenticationEntryPointImpl;
import com.hhu.spring_security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true) // Spring Security 提供的一个注解，用于启用全局方法级别的安全控制
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;


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

       //将自定义认证过滤器,添加到过滤器链中
       http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

       // 配置异常处理器
       http.exceptionHandling()
               .authenticationEntryPoint(authenticationEntryPoint) // 认证失败处理器 401
               .accessDeniedHandler(accessDeniedHandler);// 授权异常处理器 403
    }
}
