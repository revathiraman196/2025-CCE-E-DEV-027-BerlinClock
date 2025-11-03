package com.kata.assessment.berlinclock.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SingleHoursConverterServiceImplTest {

    @InjectMocks
    private SingleHoursConverterServiceImpl singleHoursConverterServiceImpl;



    @Test
    void shouldThrowExceptionForInvalidLength() {
        // Given
        String berlinTime = "RRROO"; // Invalid length (5 characters instead of 4)

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            singleHoursConverterServiceImpl.convert(berlinTime);
        });

        assertEquals("Invalid Berlin time format. Expected 4 characters in the row.", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidCharacter() {
        // Given
        String berlinTime = "RRRX"; // Invalid character 'X'

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            singleHoursConverterServiceImpl.convert(berlinTime);
        });

        assertEquals("Invalid character in Berlin time row. Only 'R', 'O', and 'Y' are allowed.", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionForEmptyRow() {
        // Given
        String berlinTime = ""; // Empty row

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            singleHoursConverterServiceImpl.convert(berlinTime);
        });

        assertEquals("Berlin time row cannot be null or empty.", thrown.getMessage());
    }



    @Test
    void shouldReturnCorrectHourForValidInput() {
        // Given
        String berlinTime = "RRRO"; // Expected 3 hours (3 'R' lamps)

        // When
        int result = singleHoursConverterServiceImpl.convert(berlinTime);

        // Then
        assertEquals(3, result, "Should convert 'RRRO' to 3 hours.");
    }

    @Test
    void shouldReturnCorrectHourForMinimumValidInput() {
        // Given
        String berlinTime = "ROOO"; // Expected 1 hour (1 'R' lamp)

        // When
        int result = singleHoursConverterServiceImpl.convert(berlinTime);

        // Then
        assertEquals(1, result, "Should convert 'ROOO' to 1 hour.");
    }
}
