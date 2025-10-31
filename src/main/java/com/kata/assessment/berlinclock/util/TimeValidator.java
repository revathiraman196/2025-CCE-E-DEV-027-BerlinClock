package com.kata.assessment.berlinclock.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DateTimeException;

public class TimeValidator {
    private static final Logger LOG= LoggerFactory.getLogger(TimeValidator.class);
    private TimeValidator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Validates that the input time string is not null or blank.
     * @param time Input time string
     * @throws IllegalArgumentException if time is null or blank
     */
    public static void validateNotEmpty(String time) {
        if (time == null || time.isBlank()) {
            LOG.error("Input time cannot be null or empty");
            throw new IllegalArgumentException("Input time cannot be null or empty");
        }
        LOG.debug("Input time cannot be null or empty :: {}", time);
    }


    /**
     * Validates that the input time string is in the correct format (HH:mm:ss).
     * <p>
     * Uses java.time.LocalTime parsing to ensure the input is a valid time.
     * If the format is invalid or the values are out of range (e.g., hour > 23),
     * an IllegalArgumentException is thrown.
     *
     * @param time the input time string to validate, expected in HH:mm:ss format
     * @throws IllegalArgumentException if the time is not in valid HH:mm:ss format
     */
    public static void validateTimeFormat(String time) {
        try {
            java.time.LocalTime.parse(time); // throws DateTimeParseException if invalid
        }catch (DateTimeException exception){
            LOG.error("Invalid Input, Expected input format is HH:mm:ss with hour 0-23");
            throw new IllegalArgumentException("Invalid Input, Expected input format is HH:mm:ss", exception);
        }
        LOG.debug("Invalid Input, Expected input format is HH:mm:ss :: {}", time);
    }
}
