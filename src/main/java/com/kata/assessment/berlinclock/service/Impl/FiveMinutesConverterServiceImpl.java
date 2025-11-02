package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import com.kata.assessment.berlinclock.util.TimeValidator;
import org.springframework.stereotype.Component;

@Component("fiveMinutesConverterServiceImpl")
public class FiveMinutesConverterServiceImpl implements RowConverterService {

    @Override
    public int convert(String row) {
        // Validate the row length to be 11 (as per the Five Minutes Row requirement).
        TimeValidator.validateBerlinTimeRow(row, 11);

        // Count occurrences of 'Y' in the row and multiply by 5 to get total minutes.
        long count = row.chars()
                .filter(ch -> ch == 'Y')
                .count();

        return (int) (count * 5);
    }
}
