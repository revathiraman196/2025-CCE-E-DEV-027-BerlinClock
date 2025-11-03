package com.kata.assessment.berlinclock.controller;

import com.kata.assessment.berlinclock.service.Impl.BerlinTimeConverterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DigitalClockControllerTest {
    @Mock
    private BerlinTimeConverterServiceImpl berlinTimeConverterService;
    private DigitalClockController digitalClockController;

    @BeforeEach
    void setUp() {
        berlinTimeConverterService = mock(BerlinTimeConverterServiceImpl.class);
        digitalClockController = new DigitalClockController(berlinTimeConverterService);
    }

    @Test
    void getDigitalClock_WithEmptyInput_ShouldReturnEmpty() {
        // Given
        String berlinTimeInput = "";
        String expectedDigitalTime = "";

        // Stub the mock
        when(berlinTimeConverterService.convertToDigitalTime(berlinTimeInput))
                .thenReturn(expectedDigitalTime);

        // When
        ResponseEntity<String> response = digitalClockController.getDigitalClock(berlinTimeInput);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDigitalTime, response.getBody());

        // Verify the service call
        verify(berlinTimeConverterService, times(1))
                .convertToDigitalTime(berlinTimeInput);
    }

    @Test
    void getDigitalClock_ShouldReturnDigitalTime() {
        // Given
        String berlinTimeInput = "ORROOROOOYYRYYRYOOOOYYOO";
        String expectedDigitalTime = "11:37:01";

        // Stub the mock to return the expected value
        when(berlinTimeConverterService.convertToDigitalTime(berlinTimeInput))
                .thenReturn(expectedDigitalTime);

        // When
        ResponseEntity<String> response = digitalClockController.getDigitalClock(berlinTimeInput);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDigitalTime, response.getBody());

        // Verify the service was called exactly once
        verify(berlinTimeConverterService, times(1))
                .convertToDigitalTime(berlinTimeInput);
    }


}
