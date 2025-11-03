package com.kata.assessment.berlinclock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {


    private final String secretKey;

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public JwtService(@Value("${auth.secret-key}") String secretKey) {
        this.secretKey = secretKey;
        logger.debug("JWT secret key loaded (length={}): {}", secretKey.length(), "***hidden***");
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Validates the JWT token.
     *
     * @param token the JWT token
     * @return true if valid and not expired, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseClaims(token.trim());
            if (isTokenExpired(claims)) {
                logger.warn("JWT token is expired: {}", token);
                return false;
            }
            return true;
        } catch (JwtException e) {
            logger.warn("Invalid JWT token: {}", token, e);
            return false;
        }
    }

    /**
     * Extracts the username (subject) from the JWT.
     */
    public String extractUsername(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration != null && expiration.before(new Date());
    }
}
