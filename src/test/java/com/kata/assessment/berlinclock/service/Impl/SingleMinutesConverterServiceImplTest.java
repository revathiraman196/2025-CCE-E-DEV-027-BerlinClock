package com.kata.assessment.berlinclock.service.Impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SingleMinutesConverterServiceImplTest {

    @InjectMocks
    private SingleMinutesConverterServiceImpl singleMinutesConverterServiceImpl;


    @Test
    void shouldThrowExceptionForInvalidCharacter() {
        // Given
        String berlinTime = "YYOX"; // Invalid character 'X'

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            singleMinutesConverterServiceImpl.convert(berlinTime);
        });

        assertEquals("Invalid character in Berlin time row. Only 'R', 'O', and 'Y' are allowed.", thrown.getMessage());
    }
    @Test
    void shouldThrowExceptionForInvalidLength() {
        // Given: Invalid row (e.g., 5 characters instead of 4)
        String berlinTime = "YYYYY";  // Invalid length (5 characters instead of 4)

        // When & Then: Ensure the exception is thrown for invalid row length
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            singleMinutesConverterServiceImpl.convert(berlinTime);
        });

        assertEquals("Invalid Berlin time format. Expected 4 characters in the row.", thrown.getMessage());
    }



    @Test
    void shouldThrowExceptionForEmptyRow() {
        // Given
        String berlinTime = ""; // Empty row

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            singleMinutesConverterServiceImpl.convert(berlinTime);
        });

        assertEquals("Berlin time row cannot be null or empty.", thrown.getMessage());
    }

    @Test
    void shouldReturnCorrectMinutesForValidInput() {
        // Given
        String berlinTime = "YYOO"; // Expected 2 minutes (2 'Y' lamps)

        // When
        int result = singleMinutesConverterServiceImpl.convert(berlinTime);

        // Then
        assertEquals(2, result, "Should convert 'YYOO' to 2 minutes.");
    }

    @Test
    void shouldReturnCorrectMinutesForMinimumValidInput() {
        // Given
        String berlinTime = "YOOO"; // Expected 1 minute (1 'Y' lamp)

        // When
        int result = singleMinutesConverterServiceImpl.convert(berlinTime);

        // Then
        assertEquals(1, result, "Should convert 'YOOO' to 1 minute.");
    }

    @Test
    void shouldReturnCorrectMinutesForMaximumValidInput() {
        // Given
        String berlinTime = "YYYY"; // Expected 4 minutes (4 'Y' lamps)

        // When
        int result = singleMinutesConverterServiceImpl.convert(berlinTime);

        // Then
        assertEquals(4, result, "Should convert 'YYYY' to 4 minutes.");
    }
}
