package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BerlinTimeConverterServiceImplTest {

    @Mock
    private RowConverterService secondsLampConverterImp;

    @Mock
    private RowConverterService fiveHoursConverterServiceImpl;
    @Mock
    private RowConverterService singleHourConverterServiceImpl;
    @Mock
    private RowConverterService fiveMinutesConverterServiceImpl;

    private BerlinTimeConverterServiceImpl berlinTimeConverterServiceImpl;

    @BeforeEach
    void setUp() {
        berlinTimeConverterServiceImpl = new BerlinTimeConverterServiceImpl(
                fiveHoursConverterServiceImpl,
                secondsLampConverterImp,
                singleHourConverterServiceImpl,
                fiveMinutesConverterServiceImpl
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



}
