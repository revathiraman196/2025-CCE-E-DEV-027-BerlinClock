package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import com.kata.assessment.berlinclock.util.TimeValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BerlinTimeConverterServiceImpl {
    private final RowConverterService fiveHoursConverterServiceImpl;
    private final RowConverterService secondsLampConverterImp;

    public BerlinTimeConverterServiceImpl(
            @Qualifier("fiveHoursConverterServiceImpl") RowConverterService fiveHoursConverterServiceImpl,
            @Qualifier("secondsLampConverterImp") RowConverterService secondsLampConverterImp) {
        this.fiveHoursConverterServiceImpl = fiveHoursConverterServiceImpl;
        this.secondsLampConverterImp = secondsLampConverterImp;
    }

    public String convertToDigitalTime(String berlinTime) {
        // Basic validation
        TimeValidator.validateNotEmpty(berlinTime);
        TimeValidator.validateBerlinTimeFormat(berlinTime);

        // Extract the seconds part of the Berlin time and convert it
        int seconds = secondsLampConverterImp.convert(berlinTime.substring(0, 1)); // Row 1: Seconds
        // Extract the Five hours part of the Berlin time and convert it
        int fiveHours = fiveHoursConverterServiceImpl.convert(berlinTime.substring(1, 5)); // Row 2: Five Hours

        // Returning digital time as "HH:MM:SS"
        return String.format("%02d:%02d:%02d", fiveHours, 0, seconds);


    }
}
