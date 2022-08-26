package com.fastcampus.miniproject.config.auth.jwt;

public interface JwtProperties {

    String SECRET = "project";
    int EXPIRATION_TIME = 60000 * 10; // 10일 (1/10000초)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
