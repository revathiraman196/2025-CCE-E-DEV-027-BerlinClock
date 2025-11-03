package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.RowConverterService;
import com.kata.assessment.berlinclock.util.TimeValidator;
import org.springframework.stereotype.Component;

@Component("fiveHoursConverterServiceImpl")
public class FiveHoursConverterServiceImpl implements RowConverterService {
    @Override
    public int convert(String row) {
        // Logic to convert the Five Hours row (e.g., "RRRO" -> 5 hours)
        TimeValidator.validateBerlinTimeRow(row,4);

        // Count occurrences of 'R' in the row and multiply by 5
        long count = row.chars()
                .filter(ch -> ch == 'R')
                .count();

        return (int) (count * 5);
    }
}
