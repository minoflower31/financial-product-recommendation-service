package com.fastcampus.miniproject.config.filter;

import org.springframework.http.HttpStatus;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpStatus.ACCEPTED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
//        PrintWriter writer = response.getWriter();
//        writer.write("로그인 성공");
//        writer.close();

    }

}
