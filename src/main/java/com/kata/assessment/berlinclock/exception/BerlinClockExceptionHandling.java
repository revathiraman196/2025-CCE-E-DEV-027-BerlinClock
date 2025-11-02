package com.kata.assessment.berlinclock.exception;



import com.kata.assessment.berlinclock.exception.error.ErrorResponse;
import com.kata.assessment.berlinclock.exception.error.ErrorResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles exceptions globally across the Berlin Clock API.
 */
@ControllerAdvice
public class BerlinClockExceptionHandling {

    private static final Logger LOG = LoggerFactory.getLogger(BerlinClockExceptionHandling.class);

    /**
     * Handles missing required request parameters.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(
            MissingServletRequestParameterException ex,
            HttpServletRequest request) {
        LOG.error("Required parameter {} is missing", ex.getParameterName());
        String message = String.format("Required parameter '%s' is missing", ex.getParameterName());
        ErrorResponse error = ErrorResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, message, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles invalid inputs (e.g., time format errors, empty strings)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        LOG.error("IllegalArgumentException: {}", ex.getMessage());
        ErrorResponse error = ErrorResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Fallback for any unexpected server errors
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        LOG.error("Unhandled exception: ", ex);
        ErrorResponse error = ErrorResponseUtil.buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                request
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

