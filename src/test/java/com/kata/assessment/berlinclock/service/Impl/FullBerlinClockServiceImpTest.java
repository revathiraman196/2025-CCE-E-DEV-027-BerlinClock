package com.kata.assessment.berlinclock.service.Impl;

import static org.junit.jupiter.api.Assertions.*;

import com.kata.assessment.berlinclock.service.DisplayRowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FullBerlinClockServiceImpTest {

    @Mock private DisplayRowService secondsLampRowServiceImp;
    @Mock private DisplayRowService fiveHoursRowServiceImp;
    @Mock private DisplayRowService singleHoursRowServiceImp;
    @Mock private DisplayRowService fiveMinutesRowServiceImp;
    @Mock private DisplayRowService singleMinuteRowServiceImp;

    private FullBerlinClockServiceImp fullBerlinClockServiceImp;

    @BeforeEach
    void setUp() {
        fullBerlinClockServiceImp = new FullBerlinClockServiceImp(
                secondsLampRowServiceImp,
                fiveHoursRowServiceImp,
                singleHoursRowServiceImp,
                fiveMinutesRowServiceImp,
                singleMinuteRowServiceImp
        );
    }

    // ---------------------- FAILURE SCENARIOS ----------------------

    @Test
    void testNullInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fullBerlinClockServiceImp.display(null)
        );
        assertEquals("Input time cannot be null or empty", exception.getMessage());
    }

    @Test
    void testEmptyInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fullBerlinClockServiceImp.display("   ")
        );
        assertEquals("Input time cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidFormatInput() {
        String invalidTime = "aa:bb:cc   ";
        when(secondsLampRowServiceImp.display(invalidTime))
                .thenThrow(new IllegalArgumentException("Invalid time format"));

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> fullBerlinClockServiceImp.display(invalidTime)
        );

        assertEquals("Invalid time format", exception.getMessage());
        verify(secondsLampRowServiceImp).display(invalidTime);
    }


    // ---------------------- SUCCESS SCENARIOS ----------------------

    @Test
    void testFullClock_aggregatesAllServicesInOrder() {
        String time = "12:34:56  ";

        when(secondsLampRowServiceImp.display(time)).thenReturn("Y");
        when(fiveHoursRowServiceImp.display(time)).thenReturn("RRRO");
        when(singleHoursRowServiceImp.display(time)).thenReturn("RROO");
        when(fiveMinutesRowServiceImp.display(time)).thenReturn("YYRYYROOOOO");
        when(singleMinuteRowServiceImp.display(time)).thenReturn("YYOO");

        String result = fullBerlinClockServiceImp.display(time);


        assertEquals("YRRRORROOYYRYYROOOOOYYOO", result);

        verify(secondsLampRowServiceImp).display(time);
        verify(fiveHoursRowServiceImp).display(time);
        verify(singleHoursRowServiceImp).display(time);
        verify(fiveMinutesRowServiceImp).display(time);
        verify(singleMinuteRowServiceImp).display(time);
    }

    @Test
    void testFullClock_variousScenarios() {
        var testCases = Map.of(
                "00:00:00", "YOOOOOOOOOOOOOOOOOOOOOOO",
                "23:59:59", "ORRRRRRROYYRYYRYYRYYYYYY",
                "16:50:06", "YRRROROOOYYRYYRYYRYOOOOO",
                "11:37:01", "ORROOROOOYYRYYRYOOOOYYOO"
        );

        testCases.forEach((time, expected) -> {
            // Mock row outputs to match real logic expectations
            when(secondsLampRowServiceImp.display(time)).thenReturn(expected.substring(0, 1));
            when(fiveHoursRowServiceImp.display(time)).thenReturn(expected.substring(1, 5));
            when(singleHoursRowServiceImp.display(time)).thenReturn(expected.substring(5, 9));
            when(fiveMinutesRowServiceImp.display(time)).thenReturn(expected.substring(9, 20));
            when(singleMinuteRowServiceImp.display(time)).thenReturn(expected.substring(20));

            String actual = fullBerlinClockServiceImp.display(time);
            assertEquals(expected, actual, "Failed for time: " + time);
        });
    }
}
