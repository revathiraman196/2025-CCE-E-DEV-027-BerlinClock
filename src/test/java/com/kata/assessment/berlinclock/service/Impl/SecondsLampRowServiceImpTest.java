package com.kata.assessment.berlinclock.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecondsLampRowServiceImpTest {

    @InjectMocks
    private SecondsLampRowServiceImp secondsLampRowServiceImp;

    @Test
    void testNullInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> secondsLampRowServiceImp.display(null));
        assertEquals("Input time cannot be null or empty", exception.getMessage());
    }

    @Test
    void testEmptyInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> secondsLampRowServiceImp.display("    "));
        assertEquals("Input time cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> secondsLampRowServiceImp.display("invalid:time"));
    }

    @Test
    void testInvalidHour24() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> secondsLampRowServiceImp.display("24:00:00"));
        assertTrue(ex.getMessage().contains("Invalid Input"));
    }

    @Test
    void testSecondsLamp_evenSecond() {
        String lamp = secondsLampRowServiceImp.display("00:00:00"); // 0 seconds => even
        assertEquals("Y", lamp);
    }

    @Test
    void testSecondsLamp_oddSecond() {
        String lamp = secondsLampRowServiceImp.display("23:59:59"); // 59 seconds => odd
        assertEquals("O", lamp);
    }

    @Test
    void testSecondsLamp_otherCases() {
        assertEquals("Y", secondsLampRowServiceImp.display("12:34:56"));
        assertEquals("O", secondsLampRowServiceImp.display("12:34:57"));
        assertEquals("O", secondsLampRowServiceImp.display("01:01:01"));
        assertEquals("Y", secondsLampRowServiceImp.display("01:01:02"));
    }
}
