package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.DisplayRowService;
import com.kata.assessment.berlinclock.constant.BerlinClockConstants;
import com.kata.assessment.berlinclock.util.TimeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service("fiveHoursRowServiceImp")
public class FiveHoursRowServiceImp implements DisplayRowService {

    private static final Logger LOG = LoggerFactory.getLogger(FiveHoursRowServiceImp.class);

    @Override
    public String display(String time) {
        LOG.info("Entered display method for Five Hours Row");

        // Validate and convert time
        LocalTime localTime = TimeParser.validateAndConvertToLocalTime(time);

        // Build the row for the 5 hours row
        String result = buildFiveHoursRow(localTime.getHour());

        LOG.info("Returning five hours row: {}", result);
        return result;
    }

    /**
     * Builds the five hours row string for Berlin Clock.
     *
     * @param hours the hour component of the time
     * @return the five hours row as a string
     */
    private String buildFiveHoursRow(int hours) {
        int onLamps = computeOnLamps(hours);
        LOG.debug("Hours: {}, On lamps (5-hour blocks): {}", hours, onLamps);

        // Use StringBuilder to build row
        StringBuilder row = new StringBuilder();
        for (int i = 1; i <= BerlinClockConstants.FIVE_HOURS_ROW_LAMPS; i++) {
            row.append(i <= onLamps ? BerlinClockConstants.LAMP_ON_RED : BerlinClockConstants.LAMP_OFF);
        }

        return row.toString();
    }

    /**
     * Computes how many lamps should be on for the five hours row.
     *
     * @param hours the hour component of the time
     * @return number of lamps that should be on
     */
    private int computeOnLamps(int hours) {
        return hours / 5;
    }
}
