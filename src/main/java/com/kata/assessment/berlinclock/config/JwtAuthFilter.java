package com.kata.assessment.berlinclock.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kata.assessment.berlinclock.exception.error.ErrorResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public JwtAuthFilter(JwtService jwtService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String requestUri = request.getRequestURI();

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7).trim();
            LOG.debug("JWT token found for request: {}", requestUri);

            try {
                if (jwtService.isTokenValid(token)) {
                    String username = jwtService.extractUsername(token);
                    LOG.info("Authenticated user '{}' for request '{}'", username, requestUri);

                    var auth = new UsernamePasswordAuthenticationToken(
                            username, null, Collections.emptyList()
                    );
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);

                } else {
                    LOG.warn("Invalid JWT token for request '{}'", requestUri);
                    handleUnauthorized(response, request, "JWT token is invalid or expired");
                    return;
                }
            } catch (Exception e) {
                LOG.error("Error processing JWT token for request '{}': {}", requestUri, e.getMessage());
                handleUnauthorized(response, request, "Error processing JWT token: " + e.getMessage());
                return;
            }
        } else {
            LOG.debug("No JWT token found in request '{}'", requestUri);
        }

        filterChain.doFilter(request, response);
    }

    private void handleUnauthorized(HttpServletResponse response, HttpServletRequest request, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        LOG.warn("Unauthorized access to '{}': {}", request.getRequestURI(), message);

        var errorResponse = ErrorResponseUtil.buildErrorResponse(HttpStatus.UNAUTHORIZED, message, request);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
