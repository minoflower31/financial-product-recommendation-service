package com.fastcampus.miniproject.config.auth.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fastcampus.miniproject.config.auth.PrincipalDetails;
import com.fastcampus.miniproject.dto.request.LoginMemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("[JwtAuthenticationFilter.class] - 로그인 시도 중");
        try {
            ObjectMapper om = new ObjectMapper();
            LoginMemberRequest loginIdAndPassword = om.readValue(request.getInputStream(), LoginMemberRequest.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginIdAndPassword.getEmail(), loginIdAndPassword.getPassword());
            log.debug("[JwtAuthenticationFilter.class] - authenticationToken 값 : {}", authenticationToken);

            // PrincipalDetailsService 의 loadUserByUsername() 함수가 실행됨
            // authentication 에 내 로그인 정보가 담긴다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getMember().getLoginId());
            // authentication 객체가 session 영역에 저장됨 ==> 로그인이 되었다는 뜻

            // 로그인 후 response body 에 JSON 추가
            Gson gson = new Gson();
            JsonObject obj = new JsonObject();
            obj.addProperty("code", HttpStatus.OK.value());
            obj.addProperty("status", HttpStatus.OK.getReasonPhrase());
            String json = gson.toJson(obj);

            response.getWriter().write(json);
            return authentication;
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        System.out.println("=====================");
        // 2. 정상인지 로그인 시도를 해봄, authenticationManager 로 로그인 시도를 하면 PrincipalDetailsService 가 호출 loadUserByUsername() 함수가 호출
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // Hash 암호 방식
        String jwtToken = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))    // 만료시간 설정 (30분 설정)
                .withClaim("loginId", principalDetails.getMember().getLoginId())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);  // "Bearer " <- 한칸 띄어쓰기 해줘야함
    }
}

