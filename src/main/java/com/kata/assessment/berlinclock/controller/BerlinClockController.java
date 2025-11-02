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
    private final DisplayRowService singleHoursRowService;
    private final DisplayRowService fiveHoursRowService;
    private final DisplayRowService secondsLampRowService;

    private static final Logger LOG = LoggerFactory.getLogger(BerlinClockController.class);

    public BerlinClockController(
            @Qualifier("singleMinuteRowServiceImp") DisplayRowService singleMinuteRowService,
            @Qualifier("fiveMinutesRowServiceImp") DisplayRowService fiveMinutesRowService,
            @Qualifier("singleHoursRowServiceImp") DisplayRowService singleHoursRowService,
            @Qualifier("fiveHoursRowServiceImp") DisplayRowService fiveHoursRowService,
            @Qualifier("secondsLampRowServiceImp") DisplayRowService secondsLampRowService
    ) {
        this.singleMinuteRowService = singleMinuteRowService;
        this.fiveMinutesRowService = fiveMinutesRowService;
        this.singleHoursRowService = singleHoursRowService;
        this.fiveHoursRowService = fiveHoursRowService;
        this.secondsLampRowService = secondsLampRowService;
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
    @GetMapping("/single-hours-row")
    public ResponseEntity<String> getSingleHoursRow(@RequestParam(name = "time") String time) {
        LOG.info("Entered getSingleHoursRow method");
        String result = singleHoursRowService.display(time);
        return ResponseEntity.ok(result);
    }
    /**
     * Returns the Berlin Clock five hours row for the given time.
     *
     * @param time the time in HH:mm:ss format
     * @return the string representing the five hours row
     */
    @GetMapping("/five-hours-row")
    public ResponseEntity<?> getFiveHoursRow(@RequestParam(name = "time") String time) {
        LOG.info("Entered getFiveHoursRow method");
        String result = fiveHoursRowService.display(time);
        return ResponseEntity.ok(result);
    }
    /**
     * Returns the Berlin Clock seconds lamp for the given time.
     *
     * @param time the time in HH:mm:ss format
     * @return the string representing the seconds lamp ("Y" or "O")
     */
    @GetMapping("/seconds-lamp-row")
    public ResponseEntity<String> getSecondsLampRow(@RequestParam(name = "time") String time) {
        LOG.info("Entered getSecondsLampRow method");
        String lamp = secondsLampRowService.display(time);
        LOG.info("Returning seconds lamp row: {}", lamp);
        return ResponseEntity.ok(lamp);
    }


}
