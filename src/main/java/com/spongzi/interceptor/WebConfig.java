package com.spongzi.interceptor;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
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
            "/user/login",
            "/user/register",
            "/user/sendMsg",
            "/user/sendEmail"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("我执行了~~~~");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
    }
}