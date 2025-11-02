package com.kata.assessment.berlinclock.service.Impl;
import com.kata.assessment.berlinclock.service.DisplayRowService;
import com.kata.assessment.berlinclock.constant.BerlinClockConstants;
import com.kata.assessment.berlinclock.util.TimeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service("singleHoursRowServiceImp")
public class SingleHoursRowServiceImp implements DisplayRowService {

    private static final Logger LOG = LoggerFactory.getLogger(SingleHoursRowServiceImp.class);

    @Override
    public String display(String time) {
        LOG.info("Entered display method for Single Hours Row");

        // Validate and parse the time
        LocalTime localTime = TimeParser.validateAndConvertToLocalTime(time);

        // Build the single hours row
        String result = buildSingleHoursRow(localTime.getHour());

        LOG.info("Returning single hours row: {}", result);
        return result;
    }

    /**
     * Builds the single hours row string for Berlin Clock.
     *
     * @param hour the hour component of the time
     * @return the single hours row as a string
     */
    private String buildSingleHoursRow(int hour) {
        int onLamps = hour % 5;
        int offLamps = BerlinClockConstants.SINGLE_HOURS_ROW_LAMPS - onLamps;

        return BerlinClockConstants.LAMP_ON_RED.repeat(onLamps)
                + BerlinClockConstants.LAMP_OFF.repeat(offLamps);
    }
}
