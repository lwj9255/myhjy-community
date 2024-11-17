package com.hhu.spring_security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrosConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许 Cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 设置允许的 Header 属性
                .allowedHeaders("*")
                // 设置跨域允许时间
                .maxAge(3600);
    }
}
