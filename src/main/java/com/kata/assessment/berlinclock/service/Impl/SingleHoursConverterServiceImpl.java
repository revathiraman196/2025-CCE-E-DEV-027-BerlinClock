package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import com.kata.assessment.berlinclock.util.TimeValidator;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("singleHourConverterServiceImpl")
public class SingleHoursConverterServiceImpl implements RowConverterService {

    private static final Logger LOG = LoggerFactory.getLogger(SingleHoursConverterServiceImpl.class);

    @Override
    public int convert(String row) {
        LOG.debug("Converting single hour row: {}", row);

        // Validate the row format for Single Hours (4 lamps, each 'R' or 'O')
        TimeValidator.validateBerlinTimeRow(row, 4);

        // Count occurrences of 'R' and multiply by 1 (since each lamp represents 1 hour)
        long count = row.chars()
                .filter(ch -> ch == 'R') // Filter only 'R' characters
                .count();  // Count occurrences of 'R'

        int result = (int) count;

        LOG.debug("Row '{}' contains {} 'R' lamps, converting to {} hours.", row, count, result);

        return result;
    }
}

