package com.kata.assessment.berlinclock.service.Impl;

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
        return "";
    }
}
