package com.yangy.config;

import com.yangy.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法决定是否需要登录
                .excludePathPatterns(
                        "/sms/admin/user/login",
                        "/sms/user/user/login",
                        "/**/export","/**/import"
                        ,"/sms/file/**"
                        ,"/**/swagger-resources/**"
                        ,"/**/webjars/**"
                        ,"/**/v2/**"
                        ,"/**/swagger-ui/**");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
