package com.kata.assessment.berlinclock.service;

public interface RowConverterService {
    /**
     * Converts a portion of the Berlin Clock row into its numeric value.
     * @param rowString Berlin Clock row string
     * @return numeric value (hours, minutes, seconds)
     */
    int convert(String rowString);
}
