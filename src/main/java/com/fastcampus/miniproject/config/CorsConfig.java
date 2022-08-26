package com.fastcampus.miniproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);   // 내 서버가 응답을 할 때 JSON 을 자바스크립트에서 처리할 수 있게 할지 설정
        config.addAllowedOrigin("*");   // 모든 IP 에 응답을 허용
        config.addAllowedHeader("*");   // 모든 header 에 응답 허용
        config.addAllowedMethod("*");   // 모든 메서드 요청을 허용
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
