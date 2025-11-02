package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BerlinTimeConverterServiceImplTest {

    @Mock
    private RowConverterService secondsConverter;

    @Mock
    private RowConverterService fiveHoursConverter;

    @InjectMocks
    private BerlinTimeConverterServiceImpl berlinTimeConverterServiceImpl;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testConvertToDigitalTimeWithEmptyInput() {
        // given
        String berlinTime = "";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> berlinTimeConverterServiceImpl.convertToDigitalTime(berlinTime),
                "Should throw IllegalArgumentException for empty input.");
    }

    @Test
    void testConvertToDigitalTimeWithInvalidFormat() {
        // given
        String berlinTime = "INVALIDTIME";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> berlinTimeConverterServiceImpl.convertToDigitalTime(berlinTime),
                "Should throw IllegalArgumentException for invalid format.");
    }
}
