package com.fastcampus.miniproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable().authorizeRequests()
                .and()
                .httpBasic()
                .disable()
                .authorizeRequests()
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
                ).permitAll()
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .logout()
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
