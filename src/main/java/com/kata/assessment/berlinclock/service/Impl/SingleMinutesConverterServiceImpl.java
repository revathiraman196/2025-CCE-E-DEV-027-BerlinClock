package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import com.kata.assessment.berlinclock.util.TimeValidator;
import org.springframework.stereotype.Component;

@Component("singleMinutesConverterServiceImpl")
public class SingleMinutesConverterServiceImpl implements RowConverterService {

    @Override
    public int convert(String row) {
        // Validate that the row has exactly 4 characters, as per the Single Minute Row requirement.
        TimeValidator.validateBerlinTimeRow(row, 4);

        // Count the occurrences of 'Y' in the row
        long count = row.chars()
                .filter(ch -> ch == 'Y')
                .count();
        return (int) count;
    }
}
