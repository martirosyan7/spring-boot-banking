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
                        .requestMatchers("/api/user/register/**", "/api/user/login", "/api/user").permitAll()
                        .requestMatchers("/api/user/me", "/api/user/transactions").authenticated()
                        .requestMatchers("/api/accounts", "/api/accounts/{id}").permitAll()
                        .requestMatchers("/api/transactions/account/**").authenticated()
                        .requestMatchers("/api/transactions/card/deposit").authenticated()
                        .requestMatchers("/api/cards", "/api/cards/{id}").permitAll()
                        .requestMatchers("/api/currency/convert").permitAll()
                        .requestMatchers("/api/accounts/create", "/api/cards/create").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}