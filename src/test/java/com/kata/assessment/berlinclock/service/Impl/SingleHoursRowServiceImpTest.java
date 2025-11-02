package com.kata.assessment.berlinclock.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import com.kata.assessment.berlinclock.service.Impl.SingleHoursRowServiceImp;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SingleHoursRowServiceImpTest {

    @InjectMocks
    private SingleHoursRowServiceImp singleHoursRowServiceImp;

    // ---------------------- FAILURE SCENARIOS ----------------------

    @Test
    void testNullInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> singleHoursRowServiceImp.display(null)
        );
        assertEquals("Input time cannot be null or empty", exception.getMessage());
    }

    @Test
    void testEmptyInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> singleHoursRowServiceImp.display("   ")
        );
        assertEquals("Input time cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidFormatInput() {
        assertThrows(IllegalArgumentException.class, () -> singleHoursRowServiceImp.display("yy:12:12"));
    }

    @Test
    void testInvalidHour24() {
        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> singleHoursRowServiceImp.display("24:59:59")
        );
        assertTrue(ex.getMessage().contains("Invalid Input"));
    }

    // ---------------------- SUCCESS SCENARIOS ----------------------

    @Test
    void testSingleHoursRow_successScenarios() {
        var testCases = Map.of(
                "00:00:00", "OOOO",  // 0 single hours
                "23:59:59", "RRRO",  // 3 single hours
                "02:04:00", "RROO",  // 2 single hours
                "08:23:00", "RRRO",  // 3 single hours
                "14:35:00", "RRRR"   // 4 single hours
        );

        testCases.forEach((time, expectedRow) -> {
            String actualRow = singleHoursRowServiceImp.display(time);
            assertEquals(expectedRow, actualRow, "Failed for time: " + time);
        });
    }
}
