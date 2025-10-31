package com.kata.assessment.berlinclock.util;

import java.time.LocalTime;

import static com.kata.assessment.berlinclock.util.TimeValidator.validateNotEmpty;
import static com.kata.assessment.berlinclock.util.TimeValidator.validateTimeFormat;

public class TimeParser {
    private TimeParser() {
        // Private constructor to prevent instantiation
    }
    /**
     * Validates the input time string and converts it to a LocalTime.
     *
     * @param time the time string in HH:mm:ss format
     * @return LocalTime object representing the input time
     * @throws IllegalArgumentException if the input is empty or invalid
     */
    public static LocalTime validateAndConvertToLocalTime(String time) {
        validateNotEmpty(time);
        validateTimeFormat(time);
        return LocalTime.parse(time);
    }
}
