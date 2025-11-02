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
            throw new IllegalArgumentException("Invalid Input, Expected input format is HH:mm:ss with hour 0-23" , exception);
        }
        LOG.debug("Invalid Input, Expected input format is HH:mm:ss :: {}", time);
    }

    /**
     * Validates that the input time string is in the correct Berlin Time format.
     * Expected format: 24 chars, each representing a row of the Berlin Clock.
     * @param time the input Berlin Clock string
     * @throws IllegalArgumentException if the time format is invalid
     */
    public static void validateBerlinTimeFormat(String time) {
        if (time.trim().length()!= 24) {
            LOG.error("Invalid Berlin Time format, expected 24 characters.");
            throw new IllegalArgumentException("Invalid Berlin Time format, expected 24 characters.");
        }

        LOG.debug("Berlin time format is valid: {}", time);
    }

    /**
     * Validates that the Berlin time row is in the correct format.
     * Expected format: 4 characters, each representing a lamp (either 'R' or 'O').
     *
     * @param row The Berlin time row string to validate.
     * @param expectedLength The expected length of the row (e.g., 4 for Five Hours row).
     * @throws IllegalArgumentException if the row format is invalid.
     */
    public static void validateBerlinTimeRow(String row, int expectedLength) {
        if (row == null || row.trim().isEmpty()) {
            LOG.error("Berlin time row cannot be null or empty.");
            throw new IllegalArgumentException("Berlin time row cannot be null or empty.");
        }

        // Validate that the row only contains valid characters ('R' and 'O')
        validateValidCharacters(row);

        // Validate the row length
        if (row.trim().length() != expectedLength) {
            LOG.error("Invalid Berlin time row format, expected {} characters.", expectedLength);
            throw new IllegalArgumentException("Invalid Berlin time format. Expected " + expectedLength + " characters in the row.");
        }

        LOG.debug("Berlin time row format is valid: {}", row);
    }

    /**
     * Validates that the Berlin time row only contains valid characters ('R' and 'O').
     *
     * @param row the row string to validate
     * @throws IllegalArgumentException if the row contains invalid characters
     */
    private static void validateValidCharacters(String row) {
        for (char ch : row.toCharArray()) {
            if (ch != 'R' && ch != 'O' && ch != 'Y') {
                LOG.error("Invalid character detected in Berlin time row: {}", ch);
                throw new IllegalArgumentException("Invalid character in Berlin time row. Only 'R', 'O', and 'Y' are allowed.");
            }
        }
        LOG.debug("Valid characters in row: {}", row);
    }

}
