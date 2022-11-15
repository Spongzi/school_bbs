package com.spongzi.interceptor;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web服务配置
 *
 * @author spongzi
 */
@Log
@Configuration
public class WebConfig implements WebMvcConfigurer {

    String[] excludePathPatterns = new String[]{
            // 放行用户需要的接口
            "/user/login",
            "/user/register",
            "/user/sendMsg",
            "/user/sendEmail",
            "/user/password",
            // 放行管理员登录接口
            "/admin/login",
            // 放行knife4j需要的静态资源
            "/doc.html",
            "/swagger-resources/**",
            // "/swagger-resources/configuration",
            "/v3/api-docs",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("我执行了~~~~");
        registry.addInterceptor(getInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
    }

    @Bean
    public LoginInterceptor getInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        // 允许跨域的头部信息
        config.addAllowedHeader("*");
        // 允许跨域的方法
        config.addAllowedMethod("*");
        // 可访问的外部域
        config.addAllowedOrigin("*");
        // 需要跨域用户凭证（cookie、HTTP认证及客户端SSL证明等）
        // config.setAllowCredentials(true);
        // config.addAllowedOriginPattern("*");

        // 跨域路径配置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}