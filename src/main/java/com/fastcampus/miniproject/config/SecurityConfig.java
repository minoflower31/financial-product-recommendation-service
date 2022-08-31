package com.fastcampus.miniproject.config;

import com.fastcampus.miniproject.config.auth.jwt.JwtAuthenticationFilter;
import com.fastcampus.miniproject.config.auth.jwt.JwtAuthorizationFilter;
import com.fastcampus.miniproject.config.filter.LoginSuccessHandler;
import com.fastcampus.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberRepository memberRepository;
    private final CorsFilter corsFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
//        usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
//        usernamePasswordAuthenticationFilter.setUsernameParameter("email");
//        usernamePasswordAuthenticationFilter.setPasswordParameter("password");
//        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
//        http.addFilter(usernamePasswordAuthenticationFilter);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable().authorizeRequests()
                .and()
                .httpBasic()
                .disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))   // 필터에 다시 등록    // AuthenticationManager 을 보내줘야함
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository))
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**"
                        , "/"
                        , "/v2/api-docs"
                        , "/swagger-ui/index.html"
                        , "/swagger-resources/**"
                        , "/swagger-ui.html"
                        , "/swagger/**"
                        , "/webjars/**"
                        , "/h2-console/**"
                        , "/favicon.ico"
                ).permitAll()
                .anyRequest()
                .permitAll()
                .and()
                .addFilter(corsFilter)
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .successForwardUrl("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll();
        //        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers("/api/v1/auth/**"
//                        , "/"
//                        , "/v2/api-docs"
//                        , "/swagger-resources/**"
//                        , "/swagger-ui/index.html"
//                        , "/swagger-ui.html"
//                        , "/webjars/**"
//                        , "/swagger/**"
//                        , "/h2-console/**"
//                        , "/favicon.ico"
//                ).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();

    }
}
