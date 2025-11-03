package com.kata.assessment.berlinclock.config;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// TODO: Enhance SecurityConfig to handle multi-functional system requirements.

@Configuration
@EnableWebSecurity
public class BerlinClockSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final static Logger LOG= LoggerFactory.getLogger(BerlinClockSecurityConfig.class);

    public BerlinClockSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // TODO: Adapt and configure CORS settings appropriately for the application.
        // TODO: Implement HTTP header security configurations (e.g., Content Security Policy, HSTS, X-Frame-Options, etc.).
        // TODO: Enable role-based access control using GrantedAuthority or similar mechanisms if required.

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/berlin-clock/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint((request, response, authException) ->
                {
                    LOG.error("Unauthorized request: {}{}", request.getRequestURI(), request.getHeader("Authorization"));
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                }));

        return http.build();
    }
}
