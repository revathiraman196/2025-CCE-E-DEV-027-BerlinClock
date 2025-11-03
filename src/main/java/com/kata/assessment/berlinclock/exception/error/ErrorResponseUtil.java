package com.kata.assessment.berlinclock.exception.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Utility class for creating standard error responses.
 */
public class ErrorResponseUtil {

    /**
     * Builds an ErrorResponse with the current timestamp.
     *
     * @param status  HTTP status
     * @param message Detailed error message
     * @param request HttpServletRequest to get request URI
     * @return ErrorResponse object
     */
    public static ErrorResponse buildErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
    }
}

