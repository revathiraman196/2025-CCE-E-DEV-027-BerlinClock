package com.kata.assessment.berlinclock.controller;

import com.kata.assessment.berlinclock.exception.error.ErrorResponse;
import com.kata.assessment.berlinclock.service.DisplayRowService;
import com.kata.assessment.berlinclock.service.Impl.SingleMinuteRowServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/berlin-clock/v1")
public class BerlinClockController {
    private final DisplayRowService singleMinuteRowService;
    private static final Logger LOG= LoggerFactory.getLogger(BerlinClockController.class);

    public BerlinClockController(DisplayRowService singleMinuteRowService) {
        this.singleMinuteRowService = singleMinuteRowService;
    }

    /**
     * Returns the Berlin Clock single minute row for the given time.
     *
     * @param time the time in HH:mm:ss format
     * @return the string representing the single minute row
     */
    @GetMapping("/single-minute-row")
    public ResponseEntity<?> getSingleMinuteRow(@RequestParam String time, HttpServletRequest request) {
        try {
            String result = singleMinuteRowService.display(time);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException ex) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    ex.getMessage(),
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
