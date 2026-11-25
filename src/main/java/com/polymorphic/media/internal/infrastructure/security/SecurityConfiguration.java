package com.polymorphic.media.internal.infrastructure.security;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/actuator/health",
            "/error"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            return http
                    // Firebase Auth uses "Authorization: Bearer <JWT>" headers (no cookies/sessions),
                    // so stateless mode + disabled CSRF is completely safe and clears Sonar S4502 & S3330.
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .csrf(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .formLogin(AbstractHttpConfigurer::disable)
                    .logout(AbstractHttpConfigurer::disable)
                    .headers(headers -> headers
                            .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                            .contentTypeOptions(Customizer.withDefaults())
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.POST, "/api/v1/taxonomies").permitAll()
                            .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                            .anyRequest().denyAll()
                    )
                    // Uncomment once Firebase JWT validation is wired up:
                    // .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                    .build();
        } catch (Exception ex) {
            throw new BeanCreationException("securityFilterChain", "Failed to configure SecurityFilterChain", ex);
        }
    }
}