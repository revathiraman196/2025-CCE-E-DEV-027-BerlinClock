package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import org.springframework.stereotype.Component;

import static com.kata.assessment.berlinclock.constant.BerlinClockConstants.LAMP_ON_YELLOW;

@Component("secondsLampConverterImp")
public class SecondsLampConverterImp implements RowConverterService {

    @Override
    public int convert(String row) {
        // A simple implementation for Seconds Lamp: If row is "Y", it's odd; if "O", it's even
        return row.equals(LAMP_ON_YELLOW) ? 1 : 0; // Odd = 1, Even = 0
    }
}
