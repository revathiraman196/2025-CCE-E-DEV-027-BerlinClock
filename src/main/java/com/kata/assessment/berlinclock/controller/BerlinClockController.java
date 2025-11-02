package com.kata.assessment.berlinclock.controller;

import com.kata.assessment.berlinclock.service.DisplayRowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/berlin-clock/v1")
public class BerlinClockController {
    private final DisplayRowService singleMinuteRowService;
    private final DisplayRowService fiveMinutesRowService;
    private static final Logger LOG= LoggerFactory.getLogger(BerlinClockController.class);

    public BerlinClockController(@Qualifier("singleMinuteRowServiceImp")DisplayRowService singleMinuteRowService,
                                 @Qualifier("fiveMinutesRowServiceImp")DisplayRowService fiveMinutesRowService) {
        this.singleMinuteRowService = singleMinuteRowService;
        this.fiveMinutesRowService = fiveMinutesRowService;
    }

    /**
     * Returns the Berlin Clock single minute row for the given time.
     *
     * @param time the time in HH:mm:ss format
     * @return the string representing the single minute row
     */
    @GetMapping("/single-minute-row")
    public ResponseEntity<?> getSingleMinuteRow(@RequestParam (name = "time") String time ) {
        LOG.info("Entered  getSingleMinuteRow method ");
        String result = singleMinuteRowService.display(time);
        return ResponseEntity.ok(result);
    }

    /**
     * Returns the Berlin Clock five minutes row for the given time.
     *
     * @param time the time in HH:mm:ss format
     * @return the string representing the five minutes row
     */
    @GetMapping("/five-minutes-row")
    public ResponseEntity<?> getFiveMinutesRow(@RequestParam(name = "time") String time) {
        LOG.info("Entered getFiveMinutesRow method");
        String result = fiveMinutesRowService.display(time);
        return ResponseEntity.ok(result);
    }

}
