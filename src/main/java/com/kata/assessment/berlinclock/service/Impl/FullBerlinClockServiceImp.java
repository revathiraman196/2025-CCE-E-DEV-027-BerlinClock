package com.kata.assessment.berlinclock.service.Impl;

import com.kata.assessment.berlinclock.service.DisplayRowService;
import com.kata.assessment.berlinclock.util.TimeValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("fullBerlinClockServiceImp")
public class FullBerlinClockServiceImp implements DisplayRowService {

    private final List<DisplayRowService> rowServices;

    public FullBerlinClockServiceImp(
            @Qualifier("secondsLampRowServiceImp") DisplayRowService secondsLampService,
            @Qualifier("fiveHoursRowServiceImp") DisplayRowService fiveHoursService,
            @Qualifier("singleHoursRowServiceImp") DisplayRowService singleHoursService,
            @Qualifier("fiveMinutesRowServiceImp") DisplayRowService fiveMinutesService,
            @Qualifier("singleMinuteRowServiceImp") DisplayRowService singleMinutesService) {

        // Keep the rows in Berlin Clock order
        this.rowServices = List.of(
                secondsLampService,
                fiveHoursService,
                singleHoursService,
                fiveMinutesService,
                singleMinutesService
        );
    }

    @Override
    public String display(String time) {
        TimeValidator.validateNotEmpty(time);
        // Aggregate all rows in order
        return rowServices.stream()
                .map(service -> service.display(time))
                .collect(Collectors.joining());
    }
}

