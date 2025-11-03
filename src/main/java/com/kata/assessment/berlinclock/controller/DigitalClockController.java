package com.kata.assessment.berlinclock.controller;

import com.kata.assessment.berlinclock.service.DisplayRowService;
import com.kata.assessment.berlinclock.service.Impl.BerlinTimeConverterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/digital-clock/v1")
public class DigitalClockController {

    private static final Logger LOG = LoggerFactory.getLogger(DigitalClockController.class);

   private final BerlinTimeConverterServiceImpl berlinTimeConverterService;

    public DigitalClockController(BerlinTimeConverterServiceImpl berlinTimeConverterService) {
        this.berlinTimeConverterService = berlinTimeConverterService;
    }

    /**
     * Converts a Berlin Clock time string to its digital time representation.
     *
     * @param berlinTime the Berlin Clock input string
     * @return a {@link ResponseEntity} containing the digital time in "HH:mm:ss" format
     */
    @GetMapping("/berlin-to-digital")
    public ResponseEntity<String> getDigitalClock(@RequestParam(name="berlinTime") String time) {
        LOG.info("Entered getDigitalClock method with time={}", time);
        String digitalTime = berlinTimeConverterService.convertToDigitalTime(time);
        LOG.info("Returning DigitalClock Clock: {}", digitalTime);
        return ResponseEntity.ok(digitalTime);
    }



}
