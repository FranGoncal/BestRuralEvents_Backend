package com.bestRuralEvents.ContentService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Temporary MVP/dev: allow all content-service endpoints
                        .requestMatchers("/content/**").permitAll()

                        // H2 console for development
                        .requestMatchers("/h2-console/**").permitAll()

                        // Health checks
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()

                        .anyRequest().permitAll()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}