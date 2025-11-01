package com.kata.assessment.berlinclock.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SingleMinuteRowServiceImpTest {
    @InjectMocks
    private SingleMinuteRowServiceImp singleMinuteRowServiceImp;

    @Test
    void testNullInput(){
        IllegalArgumentException exception =assertThrows(IllegalArgumentException.class,()->singleMinuteRowServiceImp.display(null));
        assertEquals("Input time cannot be null or empty",exception.getMessage());

    }
    @Test
    void testEmptyInput(){
        IllegalArgumentException exception =assertThrows(IllegalArgumentException.class,()->singleMinuteRowServiceImp.display("       "));
        assertEquals("Input time cannot be null or empty",exception.getMessage());

    }

    @Test
    void testInvalidInput(){
        assertThrows(IllegalArgumentException.class,()->singleMinuteRowServiceImp.display("yy:12:12"));
    }

    @Test
    void testInvalidHour24() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> singleMinuteRowServiceImp.display("24:59:59"));
        assertTrue(ex.getMessage().contains("Invalid Input"));
    }
    @Test
    void testSingleMinutesRow_zeroMinutes() {
        String row = singleMinuteRowServiceImp.display("00:00:00");
        String expected = "OOOO";
        assertEquals(expected, row);
    }

    @Test
    void testSingleMinutesRow_someMinutes() {
        // 32 % 5 = 2 lamps on
        String row = singleMinuteRowServiceImp.display("12:32:00");
        assertEquals("YYOO", row);
    }

    @Test
    void testSingleMinutesRow_maxMinutes() {
        // 59 % 5 = 4 lamps on
        String row = singleMinuteRowServiceImp.display("23:59:59");
        assertEquals("YYYY", row);
    }


}