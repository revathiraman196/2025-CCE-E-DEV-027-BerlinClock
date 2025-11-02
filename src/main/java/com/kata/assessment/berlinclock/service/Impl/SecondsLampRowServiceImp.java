package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.DisplayRowService;
import com.kata.assessment.berlinclock.constant.BerlinClockConstants;
import com.kata.assessment.berlinclock.util.TimeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service("secondsLampRowServiceImp")
public class SecondsLampRowServiceImp implements DisplayRowService {

    private static final Logger LOG = LoggerFactory.getLogger(SecondsLampRowServiceImp.class);

    @Override
    public String display(String time) {
        LOG.info("Entered display method for Seconds Lamp");

        LocalTime localTime = TimeParser.validateAndConvertToLocalTime(time);

        String lamp = (localTime.getSecond() % 2 == 0)
                ? BerlinClockConstants.LAMP_ON_YELLOW
                : BerlinClockConstants.LAMP_OFF;

        LOG.info("Returning seconds lamp: {}", lamp);
        return lamp;
    }
}

