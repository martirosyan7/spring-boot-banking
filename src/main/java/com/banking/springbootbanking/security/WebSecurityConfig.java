package com.banking.springbootbanking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JWTRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/user/register/**").permitAll()
                        .requestMatchers("/api/user/login").permitAll()
                        .requestMatchers("/api/user").permitAll()
                        .requestMatchers("/api/user/me").authenticated()
                        .requestMatchers("/api/user/transactions").authenticated()
                        .requestMatchers("/api/accounts/**").permitAll()
                        .requestMatchers("/api/transactions/account/transfer").authenticated()
                        .requestMatchers("/api/transactions/account/withdraw").authenticated()
                        .requestMatchers("/api/transactions/account/deposit").authenticated()
                        .requestMatchers("/api/transactions/card/deposit").authenticated()
                        .requestMatchers("/api/transactions/card/deposit").authenticated()
                        .requestMatchers("/api/transactions/card/deposit").authenticated()
                        .requestMatchers("/api/cards/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
