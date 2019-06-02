package com.hitwh.vblog.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName LoginConfiguration
 * @Description TODO
 * @Author yf
 * @Date 2018/10/15 16:04
 * @Version 1.0
 * 如果要添加拦截器之类的，就继承 WebMvcConfigurerAdapter 类，Override 相应的方法
 */
@Configuration
public class LoginConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor());
    }
}
