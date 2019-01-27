package com.winston.config;

import com.winston.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定义配置类 注册拦截器
 */
@Configuration          //声明是配置类
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 通过@Bean注解    将定义的拦截器注册到spring容器中
     */
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    /**
     * 添加自定义的拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //通过registry注册拦截器   通过addPathPatterns添加拦截路径
        registry.addInterceptor(this.loginInterceptor()).addPathPatterns("/**");
    }
}
