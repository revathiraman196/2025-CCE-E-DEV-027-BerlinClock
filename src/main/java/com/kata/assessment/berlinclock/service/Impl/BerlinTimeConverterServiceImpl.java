package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import com.kata.assessment.berlinclock.util.TimeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BerlinTimeConverterServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(BerlinTimeConverterServiceImpl.class);

    private final RowConverterService fiveHoursConverterServiceImpl;
    private final RowConverterService secondsLampConverterImp;
    private final RowConverterService singleHourConverterServiceImpl;
    private final RowConverterService fiveMinutesConverterServiceImpl;

    public BerlinTimeConverterServiceImpl(
            @Qualifier("fiveHoursConverterServiceImpl") RowConverterService fiveHoursConverterServiceImpl,
            @Qualifier("secondsLampConverterImp") RowConverterService secondsLampConverterImp,
            @Qualifier("singleHourConverterServiceImpl") RowConverterService singleHourConverterServiceImpl,
            @Qualifier("fiveMinutesConverterServiceImpl") RowConverterService fiveMinutesConverterServiceImpl) {
        this.fiveHoursConverterServiceImpl = fiveHoursConverterServiceImpl;
        this.secondsLampConverterImp = secondsLampConverterImp;
        this.singleHourConverterServiceImpl = singleHourConverterServiceImpl;
        this.fiveMinutesConverterServiceImpl = fiveMinutesConverterServiceImpl; // Initialize the Five Minutes Converter
    }

    public String convertToDigitalTime(String berlinTime) {
        LOG.info("Entered in convertToDigitalTime method {}",berlinTime);
        // Basic validation
        TimeValidator.validateNotEmpty(berlinTime);
        TimeValidator.validateBerlinTimeFormat(berlinTime);

        // Extract the Seconds part of the Berlin time and convert it
        int seconds = secondsLampConverterImp.convert(berlinTime.substring(0, 1)); // Row 1: Seconds
        LOG.debug("Seconds converted: {}", seconds);

        // Extract the Five Hours part of the Berlin time and convert it
        int fiveHours = fiveHoursConverterServiceImpl.convert(berlinTime.substring(1, 5)); // Row 2: Five Hours
        LOG.debug("Five Hours converted: {}", fiveHours);

        // Extract the Single Hour part of the Berlin time and convert it
        int singleHour = singleHourConverterServiceImpl.convert(berlinTime.substring(5, 9)); // Row 3: Single Hour
        LOG.debug("Single Hour converted: {}", singleHour);
        // Extract the Five Minutes part of the Berlin time and convert it
        int fiveMinutes = fiveMinutesConverterServiceImpl.convert(berlinTime.substring(9, 20)); // Row 4: Five Minutes
        LOG.debug("Five Minutes converted: {}", fiveMinutes);

        // Convert Berlin time to digital format (HH:mm:ss)
        String digitalTime = String.format("%02d:%02d:%02d", fiveHours + singleHour, fiveMinutes, seconds);
        LOG.info("Converted Berlin Time to Digital Time: {}", digitalTime);

        return digitalTime;
    }
}
