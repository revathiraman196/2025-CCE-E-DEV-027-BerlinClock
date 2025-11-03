package com.kata.assessment.berlinclock.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecondsLampConverterImpTest {

    @InjectMocks
    SecondsLampConverterImp secondsLampConverter;

    @Test
    void testConvertEvenSeconds() {
        assertEquals(1, secondsLampConverter.convert("O"));
    }

    @Test
    void testConvertOddSeconds() {
        assertEquals(0, secondsLampConverter.convert("Y"));
    }
}