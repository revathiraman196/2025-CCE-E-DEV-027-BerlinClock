package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.DisplayRowService;
import com.kata.assessment.berlinclock.constant.BerlinClockConstants;
import com.kata.assessment.berlinclock.util.TimeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service("fiveMinutesRowServiceImp")
public class FiveMinutesRowServiceImp implements DisplayRowService {

    private static final Logger LOG = LoggerFactory.getLogger(FiveMinutesRowServiceImp.class);

    @Override
    public String display(String time) {
        LOG.info("Entered display method for Five Minutes Row");

        LocalTime localTime = TimeParser.validateAndConvertToLocalTime(time);

        String result = buildFiveMinutesRow(localTime.getMinute());

        LOG.info("Returning five minutes row: {}", result);
        return result;
    }

    /**
     * Builds the five minutes row string for Berlin Clock.
     *
     * @param minutes the minute component of the time
     * @return the five minutes row as a string
     */
    private String buildFiveMinutesRow(int minutes) {
        int onLamps = computeOnLamps(minutes);
        LOG.debug("Minutes: {}, On lamps (5-minute blocks): {}", minutes, onLamps);

        StringBuilder row = new StringBuilder();
        for (int i = 1; i <= BerlinClockConstants.FIVE_MINUTES_ROW_LAMPS; i++) {
            row.append(getLampState(i, onLamps));
        }
        return row.toString();
    }

    /**
     * Computes how many lamps should be on for the five minutes row.
     *
     * @param minutes the minute component of the time
     * @return number of lamps that should be on
     */
    private int computeOnLamps(int minutes) {
        return minutes / 5;
    }

    /**
     * Returns the lamp state (Y, R, O) for the given position.
     *
     * @param position the position in the row (1-based)
     * @param onLamps number of lamps that should be on
     * @return lamp state as string
     */
    private String getLampState(int position, int onLamps) {
        if (position <= onLamps) {
            // Every 3rd lamp is red for quarter hours
            return (position % 3 == 0) ? BerlinClockConstants.LAMP_ON_RED : BerlinClockConstants.LAMP_ON_YELLOW;
        } else {
            return BerlinClockConstants.LAMP_OFF;
        }
    }
}

