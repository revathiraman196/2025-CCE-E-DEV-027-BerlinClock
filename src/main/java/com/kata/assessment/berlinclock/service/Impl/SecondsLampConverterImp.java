package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import org.springframework.stereotype.Component;

import static com.kata.assessment.berlinclock.constant.BerlinClockConstants.LAMP_ON_YELLOW;

@Component("secondsLampConverterImp")
public class SecondsLampConverterImp implements RowConverterService {

    @Override
    public int convert(String row) {

        return row.equals(LAMP_ON_YELLOW) ? 0 : 1; // Odd = 1, Even = 0
    }
}
