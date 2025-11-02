package com.kata.assessment.berlinclock.controller;

import com.kata.assessment.berlinclock.exception.BerlinClockExceptionHandling;
import com.kata.assessment.berlinclock.service.DisplayRowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BerlinClockControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DisplayRowService singleMinuteRowService;

    @Mock
    private DisplayRowService fiveMinutesRowService;
    @Mock
    private DisplayRowService singleHoursRowService;
    @Mock
    private DisplayRowService fiveHoursRowService;

    private ObjectMapper objectMapper;

    private static final String SINGLE_MINUTE_BASE_URL = "/api/berlin-clock/v1/single-minute-row";
    private static final String FIVE_MINUTES_BASE_URL = "/api/berlin-clock/v1/five-minutes-row";
    private static final String SINGLE_HOURS_BASE_URL = "/api/berlin-clock/v1/single-hours-row";
    private static final String FIVE_HOURS_BASE_URL = "/api/berlin-clock/v1/five-hours-row";

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();

        BerlinClockController berlinClockController = new BerlinClockController(
                singleMinuteRowService,
                fiveMinutesRowService,
                singleHoursRowService,
                fiveHoursRowService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(berlinClockController)
                .setControllerAdvice(new BerlinClockExceptionHandling())
                .build();
    }

    // ---------------------- SINGLE MINUTE ROW TESTS ----------------------

    @Test
    void getSingleMinuteRow_nullOrEmptyTime_returnsBadRequest() throws Exception {
        performBadRequestTest(SINGLE_MINUTE_BASE_URL, singleMinuteRowService, "", "Input time cannot be null or empty");
    }

    @Test
    void getSingleMinuteRow_invalidFormatTime_returnsBadRequest() throws Exception {
        performBadRequestTest(SINGLE_MINUTE_BASE_URL, singleMinuteRowService, "25:61:00", "Invalid time format");
    }

    @Test
    void getSingleMinuteRow_otherIllegalArgument_returnsBadRequest() throws Exception {
        performBadRequestTest(SINGLE_MINUTE_BASE_URL, singleMinuteRowService, "random-string", "Unexpected input");
    }

    @Test
    void getSingleMinuteRow_validTime_returnsOk() throws Exception {
        performValidTimeTest(SINGLE_MINUTE_BASE_URL, singleMinuteRowService, "23:23:23", "YYYO");
    }

    @Test
    void getSingleMinuteRow_missingRequestParam_returnsBadRequest() throws Exception {
        performMissingParamTest(SINGLE_MINUTE_BASE_URL);
    }

    // ---------------------- FIVE MINUTES ROW TESTS ----------------------

    @Test
    void getFiveMinutesRow_nullOrEmptyTime_returnsBadRequest() throws Exception {
        performBadRequestTest(FIVE_MINUTES_BASE_URL, fiveMinutesRowService, "", "Input time cannot be null or empty");
    }

    @Test
    void getFiveMinutesRow_invalidFormatTime_returnsBadRequest() throws Exception {
        performBadRequestTest(FIVE_MINUTES_BASE_URL, fiveMinutesRowService, "25:61:00", "Invalid time format");
    }

    @Test
    void getFiveMinutesRow_otherIllegalArgument_returnsBadRequest() throws Exception {
        performBadRequestTest(FIVE_MINUTES_BASE_URL, fiveMinutesRowService, "random-string", "Unexpected input");
    }

    @Test
    void getFiveMinutesRow_validTime_returnsOk() throws Exception {
        performValidTimeTest(FIVE_MINUTES_BASE_URL, fiveMinutesRowService, "12:35:00", "YYRYYRYOOOO");
    }

    @Test
    void getFiveMinutesRow_missingRequestParam_returnsBadRequest() throws Exception {
        performMissingParamTest(FIVE_MINUTES_BASE_URL);
    }

    // ---------------------- SINGLE HOURS ROW TESTS ----------------------

    @Test
    void getSingleHoursRow_nullOrEmptyTime_returnsBadRequest() throws Exception {
        performBadRequestTest(SINGLE_HOURS_BASE_URL, singleHoursRowService, "", "Input time cannot be null or empty");
    }

    @Test
    void getSingleHoursRow_invalidFormatTime_returnsBadRequest() throws Exception {
        performBadRequestTest(SINGLE_HOURS_BASE_URL, singleHoursRowService, "25:61:00", "Invalid time format");
    }

    @Test
    void getSingleHoursRow_otherIllegalArgument_returnsBadRequest() throws Exception {
        performBadRequestTest(SINGLE_HOURS_BASE_URL, singleHoursRowService, "random-string", "Unexpected input");
    }

    @Test
    void getSingleHoursRow_validTime_returnsOk() throws Exception {
        performValidTimeTest(SINGLE_HOURS_BASE_URL, singleHoursRowService, "14:35:00", "RRRO");
    }
    // ---------------------- FIVE HOURS ROW TESTS ----------------------
    @Test
    void getFiveHoursRow_nullOrEmptyTime_returnsBadRequest() throws Exception {
        performBadRequestTest(FIVE_HOURS_BASE_URL, fiveHoursRowService, "", "Input time cannot be null or empty");
    }

    @Test
    void getFiveHoursRow_invalidFormatTime_returnsBadRequest() throws Exception {
        performBadRequestTest(FIVE_HOURS_BASE_URL, fiveHoursRowService, "25:61:00", "Invalid time format");
    }

    @Test
    void getFiveHoursRow_otherIllegalArgument_returnsBadRequest() throws Exception {
        performBadRequestTest(FIVE_HOURS_BASE_URL, fiveHoursRowService, "random-string", "Unexpected input");
    }

    @Test
    void getFiveHoursRow_validTime_returnsOk() throws Exception {
        // Example test cases
        var testCases = Map.of(
                "00:00:00", "OOOO",
                "23:59:59", "RRRR",
                "02:04:00", "OOOO",
                "08:23:00", "ROOO",
                "16:35:00", "RRRO"
        );

        for (var entry : testCases.entrySet()) {
            String time = entry.getKey();
            String expectedRow = entry.getValue();
            when(fiveHoursRowService.display(time)).thenReturn(expectedRow);

            mockMvc.perform(get(FIVE_HOURS_BASE_URL)
                            .param("time", time)
                            .accept(MediaType.TEXT_PLAIN))
                    .andExpect(status().isOk())
                    .andExpect(content().string(expectedRow));
        }
    }

    // ---------------------- HELPER METHODS ----------------------

    private void performBadRequestTest(String url, DisplayRowService service, String time, String expectedMessage) throws Exception {
        when(service.display(time)).thenThrow(new IllegalArgumentException(expectedMessage));

        mockMvc.perform(get(url)
                        .param("time", time)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.path").value(url));
    }

    private void performMissingParamTest(String url) throws Exception {
        mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Required parameter 'time' is missing"))
                .andExpect(jsonPath("$.path").value(url));
    }

    private void performValidTimeTest(String url, DisplayRowService service, String time, String expectedRow) throws Exception {
        when(service.display(time)).thenReturn(expectedRow);

        mockMvc.perform(get(url)
                        .param("time", time)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedRow));
    }
}
