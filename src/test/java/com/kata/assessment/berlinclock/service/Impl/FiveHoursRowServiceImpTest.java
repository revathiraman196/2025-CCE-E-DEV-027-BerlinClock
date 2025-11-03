package com.kata.assessment.berlinclock.service.Impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class FiveHoursRowServiceImpTest {

    @InjectMocks
    private FiveHoursRowServiceImp fiveHoursRowServiceImp;

    // ---------------------- FAILURE SCENARIOS ----------------------

    @Test
    void testNullInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fiveHoursRowServiceImp.display(null));
        assertEquals("Input time cannot be null or empty", exception.getMessage());
    }

    @Test
    void testEmptyInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fiveHoursRowServiceImp.display("   "));
        assertEquals("Input time cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidFormatInput() {
        assertThrows(IllegalArgumentException.class, () -> fiveHoursRowServiceImp.display("aa:bb:cc"));
    }

    @Test
    void testInvalidHour24() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> fiveHoursRowServiceImp.display("24:00:00"));
        assertTrue(ex.getMessage().contains("Invalid Input"));
    }

    // ---------------------- SUCCESS SCENARIOS ----------------------

    @Test
    void testFiveHoursRow() {
        var testCases = java.util.Map.of(
                "00:00:00", "OOOO",
                "23:59:59", "RRRR",
                "02:04:00", "OOOO",
                "08:23:00", "ROOO",
                "16:35:00", "RRRO",
                "12:00:00", "RROO",
                "20:15:15", "RRRR"
        );

        testCases.forEach((time, expectedRow) -> {
            String actualRow = fiveHoursRowServiceImp.display(time);
            assertEquals(expectedRow, actualRow, "Failed for time: " + time);
        });
    }
}
