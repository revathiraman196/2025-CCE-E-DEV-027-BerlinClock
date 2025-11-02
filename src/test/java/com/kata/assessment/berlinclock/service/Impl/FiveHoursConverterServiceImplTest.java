package com.kata.assessment.berlinclock.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class FiveHoursConverterServiceImplTest {

    @InjectMocks
    private  FiveHoursConverterServiceImpl fiveHoursConverterServiceImpl ;

    @Test
    void shouldThrowExceptionForInvalidCharacter() {
        // Given
        String berlinTime = "RRRX"; // Invalid character 'X'

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            fiveHoursConverterServiceImpl.convert(berlinTime);
        });

        assertEquals("Invalid character in Berlin time row. Only 'R', 'O', and 'Y' are allowed.", thrown.getMessage().trim());
    }

    @Test
    void shouldThrowExceptionForInvalidLength() {
        // Given
        String berlinTime = "RRROO"; // Invalid length (5 characters instead of 4)

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            fiveHoursConverterServiceImpl.convert(berlinTime);
        });

        assertEquals("Invalid Berlin time format. Expected 4 characters in the row.", thrown.getMessage().trim());
    }


    @Test
    void shouldReturnCorrectHourForValidInput() {
        // Given
        String berlinTime = "RRRO"; // Represents 15 hours

        // When
        int result = fiveHoursConverterServiceImpl.convert(berlinTime);

        // Then
        assertEquals(15, result, "Should convert 'RRRO' to 15 hours.");
    }



}
