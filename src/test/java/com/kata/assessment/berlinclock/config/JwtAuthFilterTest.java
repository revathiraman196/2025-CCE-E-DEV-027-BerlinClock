package com.kata.assessment.berlinclock.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

class JwtAuthFilterTest {

    private static final String VALID_TOKEN = "validToken";
    private static final String INVALID_TOKEN = "invalidToken";
    private static final String EXPIRED_TOKEN = "expiredToken";
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String API_PATH = "/api/berlin-clock";

    @Mock
    private JwtService jwtService;

    private JwtAuthFilter jwtAuthFilter;
    private ObjectMapper objectMapper;

    @Mock
    private FilterChain filterChain;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        jwtAuthFilter = new JwtAuthFilter(jwtService,objectMapper);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getRequestURI()).thenReturn(API_PATH);
    }

    @Test
    void givenValidJwt_whenFilter_thenAuthenticateUser() throws Exception {
        // given
        when(request.getHeader(AUTH_HEADER)).thenReturn(BEARER_PREFIX + VALID_TOKEN);
        when(jwtService.isTokenValid(VALID_TOKEN)).thenReturn(true);
        when(jwtService.extractUsername(VALID_TOKEN)).thenReturn("john.doe");

        // when
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(jwtService).isTokenValid(VALID_TOKEN);
        verify(jwtService).extractUsername(VALID_TOKEN);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void givenInvalidJwt_whenFilter_thenHandleUnauthorized() throws Exception {
        // given
        when(request.getHeader(AUTH_HEADER)).thenReturn(BEARER_PREFIX + INVALID_TOKEN);
        when(jwtService.isTokenValid(INVALID_TOKEN)).thenReturn(false);

        // when
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(response).setStatus(401);
        // Optionally parse ErrorResponse if jwtAuthFilter writes it
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void givenExpiredJwt_whenFilter_thenHandleUnauthorized() throws Exception {
        // given
        when(request.getHeader(AUTH_HEADER)).thenReturn(BEARER_PREFIX + EXPIRED_TOKEN);
        when(jwtService.isTokenValid(EXPIRED_TOKEN)).thenReturn(false);

        // when
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(response).setStatus(401);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void givenNoAuthorizationHeader_whenFilter_thenContinueFilterChain() throws Exception {
        // given
        when(request.getHeader(AUTH_HEADER)).thenReturn(null);

        // when
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }
}
