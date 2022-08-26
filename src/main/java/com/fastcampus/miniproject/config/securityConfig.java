package com.fastcampus.miniproject.config;

import com.fastcampus.miniproject.config.auth.jwt.JwtAuthenticationFilter;
import com.fastcampus.miniproject.config.auth.jwt.JwtAuthorizationFilter;
import com.fastcampus.miniproject.repository.EmptyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class securityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final EmptyRepository emptyRepository;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // spring security 로직을 수행하지 않고 접근하도록 설정
//        web
//                .ignoring()
//                .antMatchers("/h2-console/**"
//                        , "/favicon.ico")
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/api/v1/auth/**"
                        , "/"
                        , "/v2/api-docs"
                        , "/swagger-resources/**"
                        , "/swagger-ui/index.html"
                        , "/swagger-ui.html"
                        , "/webjars/**"
                        , "/swagger/**"
                        , "/h2-console/**"
                        , "/favicon.ico"
                ).permitAll();
//                .anyRequest().authenticated();        // WebSecurity 설정이 우선이고 여기에서 anyRequest() 요청해서 sessionManagement() 안먹혔던 것
        http.headers()
                .frameOptions()
                .sameOrigin();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))   // 필터에 다시 등록    // AuthenticationManager 을 보내줘야함
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), emptyRepository))
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('USER')")
                .antMatchers("/products").permitAll()
                .antMatchers("/products/**")
                .access("hasRole('USER')")

                .anyRequest().permitAll();
//        // 로그아웃
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout.do")) // 로그아웃
                .logoutSuccessUrl("/login.do") // 로그아웃에 성공하면 페이지 Redirect
                .invalidateHttpSession(true) // Session 초기화
                .deleteCookies("JSESSIONID");
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
}

