package com.fastcampus.miniproject.config;

import com.fastcampus.miniproject.config.auth.JwtAccessDeniedHandler;
import com.fastcampus.miniproject.config.auth.JwtAuthenticationEntryPoint;
import com.fastcampus.miniproject.config.auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // h2 database 테스트가 원활하도록 관련 API 들은 전부 무시
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/h2-console/**", "/favicon.ico")
                .antMatchers("/v2/api-docs", "/webjars/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    /**
     *
     * CORS issue 해결
     * 이유: Cors는 기본적으로 preFlight(Http Methods: OPTIONS)로 미리 요청을 보낸 후에 이에 대한 응답으로
     * 헤더의 Access Control Allow Origin(우리 코드에서는 addAllowedOriginPattern)을 확인하고 본 요청(e.g. GET, POST 등)을 보낸다.
     * 그래서 .authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll() 로 모든 PreFlight 요청에 대해 허용하고,
     * .cors().configurationSource(corsConfigurationSource())을 통해 헤더 설정이 포함된 Cors 관련 Bean을 넣으면 된다.
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and()
                // exception handling 할 때 우리가 만든 클래스를 추가
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // h2-console 을 위한 설정을 추가
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/join", "/reissue", "/logout","/products").permitAll()
                .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요
                .and()
                .logout()
                .disable()

                // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .apply(new JwtSecurityConfig(tokenProvider));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);   // 내 서버가 응답을 할 때 JSON 을 자바스크립트에서 처리할 수 있게 할지 설정
        corsConfiguration.addAllowedOriginPattern("*");   // 모든 IP 에 응답을 허용
        corsConfiguration.addAllowedHeader("*");   // 모든 header 에 응답 허용
        corsConfiguration.addAllowedMethod("*");   // 모든 메서드 요청을 허용
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.addExposedHeader("refreshToken");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
