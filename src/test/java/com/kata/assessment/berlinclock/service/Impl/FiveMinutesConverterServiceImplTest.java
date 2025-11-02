package com.kata.assessment.berlinclock.service.Impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class FiveMinutesConverterServiceImplTest {

    @InjectMocks
    private FiveMinutesConverterServiceImpl fiveMinutesConverterServiceImpl;

    @Test
    void shouldThrowExceptionForInvalidLength() {
        // Given
        String berlinTime = "YYYYY"; // Invalid length (5 characters instead of 11)

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            fiveMinutesConverterServiceImpl.convert(berlinTime);
        });

        assertEquals("Invalid Berlin time format. Expected 11 characters in the row.", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidCharacter() {
        // Given
        String berlinTime = "YYYYXYOOOO"; // Invalid character 'X'

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            fiveMinutesConverterServiceImpl.convert(berlinTime);
        });

        assertEquals("Invalid character in Berlin time row. Only 'R', 'O', and 'Y' are allowed.", thrown.getMessage());
    }

    @Test
    void shouldReturnCorrectMinutesForValidInput() {
        // Given
        String berlinTime = "YYYYYOOOOOO"; // Expected 25 minutes (5 'Y' lamps)

        // When
        int result = fiveMinutesConverterServiceImpl.convert(berlinTime);

        // Then
        assertEquals(25, result, "Should convert 'YYYYYOOOOOO' to 25 minutes.");
    }


    @Test
    void shouldReturnZeroMinutesForNoLightsOn() {
        // Given
        String berlinTime = "OOOOOOOOOOO"; // Expected 0 minutes (no 'Y' lamps)

        // When
        int result = fiveMinutesConverterServiceImpl.convert(berlinTime);

        // Then
        assertEquals(0, result, "Should convert 'OOOOOOOOOOO' to 0 minutes.");
    }
}
