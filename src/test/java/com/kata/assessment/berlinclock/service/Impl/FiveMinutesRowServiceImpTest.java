package com.kata.assessment.berlinclock.service.Impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;


@ExtendWith(MockitoExtension.class)
class FiveMinutesRowServiceImpTest {

    @InjectMocks
    private FiveMinutesRowServiceImp fiveMinutesRowService;

    // invalid input tests
    @Test
    void testNullInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fiveMinutesRowService.display(null));
        assertEquals("Input time cannot be null or empty", exception.getMessage());
    }

    @Test
    void testEmptyInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fiveMinutesRowService.display("   "));
        assertEquals("Input time cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidInputFormat() {
        assertThrows(IllegalArgumentException.class, () -> fiveMinutesRowService.display("45:bb:yy"));
    }

    @Test
    void testInvalidHour24() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> fiveMinutesRowService.display("24:00:00"));
        assertTrue(ex.getMessage().contains("Invalid Input"));
    }

    // Success scenarios
    @Test
    void testFiveMinutesRow() {
        var testCases = Map.of(
                "00:00:00", "OOOOOOOOOOO",
                "23:59:59", "YYRYYRYYRYY",
                "12:04:00", "OOOOOOOOOOO",
                "12:23:00", "YYRYOOOOOOO",
                "12:35:00", "YYRYYRYOOOO"
        );

        testCases.forEach((time, expectedRow) -> {
            String actualRow = fiveMinutesRowService.display(time);
            assertEquals(expectedRow, actualRow, "Failed for time: " + time);
        });
    }
}
