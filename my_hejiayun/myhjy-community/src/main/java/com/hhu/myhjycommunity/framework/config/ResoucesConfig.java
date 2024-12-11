package com.hhu.myhjycommunity.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 * 通用资源配置类
 */
@Configuration
public class ResoucesConfig {
    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();

        // 1,允许任何来源
        config.setAllowedOriginPatterns(Collections.singletonList("*"));

        // 2,允许任何请求头
        config.addAllowedHeader(CorsConfiguration.ALL);

        // 3,允许任何方法
        config.addAllowedMethod("*");

        // 4,允许任何凭证
        config.setAllowCredentials(true);

        // 5,对接口配置跨域设置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
