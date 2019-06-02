package com.hitwh.vblog.config;

import com.hitwh.vblog.util.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    /**
     * 注册第一个过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean firstFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new LoginFilter());
        //可不设置，默认过滤路径即为：/*
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}