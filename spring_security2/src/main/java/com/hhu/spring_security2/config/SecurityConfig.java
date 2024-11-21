package com.hhu.spring_security2.config;

import com.hhu.spring_security2.filter.KaptchaFilter;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableGlobalMethodSecurity(prePostEnabled = true) // Spring Security 提供的一个注解，用于启用全局方法级别的安全控制
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private KaptchaFilter kaptchaFilter;


    /**
     * 用于配置HTTP请求的安全处理
     * @param
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()  //开始配置允许哪些请求访问系统
                // 指定哪些请求路径允许访问
                .mvcMatchers("/login.html","/code/image").permitAll()
                // 除上述以外，指定其他所有请求都需要经过身份验证
                .anyRequest().authenticated();

        http.formLogin() // 配置表单登录
                .loginPage("/login.html") // 登录页面
                .loginProcessingUrl("/login") // 提交路径
                .usernameParameter("username") // 表单中用户名
                .passwordParameter("password") // 表单中密码
                .successHandler(successHandler)// 认证成功处理器
                .failureUrl("/login.html") // 指定登录失败后要跳转的路径为 /login.html
                .failureHandler(failureHandler);// 认证失败处理器

        http.logout()// 开启注销配置
                .logoutUrl("/logout")// 退出登录地址
                .invalidateHttpSession(true)// 退出时session是否失效，默认为true
                .clearAuthentication(true)// 退出时是否清楚认证信息，默认为true
                .logoutSuccessUrl("/login.html")// 退出后跳转的地址
                .logoutSuccessHandler(logoutSuccessHandler);// 注销成功处理器

        http.addFilterBefore(kaptchaFilter,UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable(); // 这里先关闭 CSRF
    }

}
