package com.kata.assessment.berlinclock.exception.error;

import java.time.LocalDateTime;


/**
 * Standard API error response.
 * Immutable, used for sending error details to clients.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
    /**
     * Constructor with default timestamp (current time).
     *
     * @param status HTTP status code
     * @param error Short error description
     * @param message Detailed error message
     * @param path Request path that caused the error
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path);
    }
}
