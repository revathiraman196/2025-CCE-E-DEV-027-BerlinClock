package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BerlinTimeConverterServiceImplTest {

    @Mock
    private RowConverterService secondsLampConverterImp;

    @Mock
    private RowConverterService fiveHoursConverterServiceImpl;

    @Mock
    private RowConverterService singleHoursConverterServiceImpl;

    @Mock
    private RowConverterService fiveMinutesConverterServiceImpl;

    @Mock
    private RowConverterService singleMinutesConverterServiceImpl;

    private BerlinTimeConverterServiceImpl berlinTimeConverterServiceImpl;


    @BeforeEach
    void setUp() {
        berlinTimeConverterServiceImpl = new BerlinTimeConverterServiceImpl(
                fiveHoursConverterServiceImpl,
                secondsLampConverterImp,
                singleHoursConverterServiceImpl,
                fiveMinutesConverterServiceImpl,
                singleMinutesConverterServiceImpl
        );
    }

    @Test
    void testConvertToDigitalTimeWithEmptyInput() {
        // Given
        String berlinTime = "";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> berlinTimeConverterServiceImpl.convertToDigitalTime(berlinTime),
                "Should throw IllegalArgumentException for empty input.");
    }

    @Test
    void testConvertToDigitalTimeWithInvalidFormat() {
        // Given
        String berlinTime = "INVALIDTIME";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> berlinTimeConverterServiceImpl.convertToDigitalTime(berlinTime),
                "Should throw IllegalArgumentException for invalid format.");
    }

    @Test
    void testConvertToDigitalTimeWithInvalidLength() {
        // Given
        String berlinTime = "YRRROOOO"; // Invalid length (7 characters instead of 24)

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> berlinTimeConverterServiceImpl.convertToDigitalTime(berlinTime),
                "Should throw IllegalArgumentException for invalid length.");
    }

    @Test
    void testConvertToDigitalTimeWithInvalidCharacter() {
        // Given
        String berlinTime = "YRRRXOOO"; // Invalid character ('X' instead of 'O' or 'R')

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> berlinTimeConverterServiceImpl.convertToDigitalTime(berlinTime),
                "Should throw IllegalArgumentException for invalid character.");
    }
    @Test
    void convertToDigitalTime_YOOOOOOOOOOOOOOOOOOOOOOO_ShouldReturn000000() {
        String berlinTime = "YOOOOOOOOOOOOOOOOOOOOOOO";

        // Mock each row conversion
        when(secondsLampConverterImp.convert("Y")).thenReturn(0); // Even second = 0
        when(fiveHoursConverterServiceImpl.convert("OOOO")).thenReturn(0); // 0 hours
        when(singleHoursConverterServiceImpl.convert("OOOO")).thenReturn(0); // 0 hours
        when(fiveMinutesConverterServiceImpl.convert("OOOOOOOOOOO")).thenReturn(0); // 0 minutes
        when(singleMinutesConverterServiceImpl.convert("OOOO")).thenReturn(0); // 0 minutes

        String digitalTime = berlinTimeConverterServiceImpl.convertToDigitalTime(berlinTime);

        assertEquals("00:00:00", digitalTime);
    }
    @Test
    void convertToDigitalTime_ORROOROOOYYRYYRYOOOOYYOO_ShouldReturn113701() {
        String berlinTime = "ORROOROOOYYRYYRYOOOOYYOO";

        // Mock each row conversion
        when(secondsLampConverterImp.convert("O")).thenReturn(1);          // seconds = 1
        when(fiveHoursConverterServiceImpl.convert("RROO")).thenReturn(10); // 2 'R' lamps → 2*5 = 10 hours
        when(singleHoursConverterServiceImpl.convert("ROOO")).thenReturn(1); // 1 'R' lamp → 1 hour
        when(fiveMinutesConverterServiceImpl.convert("YYRYYRYOOOO")).thenReturn(35); // 7 yellow/red lamps → 35 min
        when(singleMinutesConverterServiceImpl.convert("YYOO")).thenReturn(2); // 2 'Y' lamps → 2 min

        String digitalTime = berlinTimeConverterServiceImpl.convertToDigitalTime(berlinTime);

        assertEquals("11:37:01", digitalTime);
    }






}
