package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.constant.BerlinClockConstants;
import com.kata.assessment.berlinclock.service.DisplayRowService;
import com.kata.assessment.berlinclock.util.TimeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class SingleMinuteRowServiceImp implements DisplayRowService {

    private static final Logger LOG= LoggerFactory.getLogger(SingleMinuteRowServiceImp.class);

    @Override
    public String display(String time) {

        LOG.info("Entered  display method ");

        //get minutes from time
        LocalTime localTime= TimeParser.validateAndConvertToLocalTime(time);

        int onLamps = localTime.getMinute() % 5;
        LOG.debug("Minutes: {}, On lamps: {}", localTime.getMinute(), onLamps);

        String onLampsStr = BerlinClockConstants.LAMP_ON_YELLOW.repeat(onLamps);
        String offLampsStr = BerlinClockConstants.LAMP_OFF.repeat(BerlinClockConstants.SINGLE_MINUTES_ROW_LAMPS - onLamps);

        String result = onLampsStr + offLampsStr;
        LOG.info("Returning single minutes row: {}", result);
        return result;
    }
}
