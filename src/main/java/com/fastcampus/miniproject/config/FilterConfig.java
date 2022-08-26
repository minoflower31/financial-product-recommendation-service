package com.fastcampus.miniproject.config;

import com.fastcampus.miniproject.config.filter.ProjectFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class FilterConfig {
    @Bean
    public FilterRegistrationBean<ProjectFilter> filter() {
        FilterRegistrationBean<ProjectFilter> bean = new FilterRegistrationBean<>(new ProjectFilter());
        bean.addUrlPatterns("/*");  // 모든 요청
        bean.setOrder(0);
        return bean;
    }
}
