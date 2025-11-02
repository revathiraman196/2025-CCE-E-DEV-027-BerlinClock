package com.kata.assessment.berlinclock.controller;

import com.kata.assessment.berlinclock.exception.BerlinClockExceptionHandling;
import com.kata.assessment.berlinclock.service.DisplayRowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BerlinClockControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DisplayRowService singleMinuteRowService;

    @InjectMocks
    private BerlinClockController berlinClockController;

    private ObjectMapper objectMapper;

    private static final String SINGLE_MINUTE_BASE_URL = "/api/berlin-clock/v1/single-minute-row";

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(berlinClockController)
                .setControllerAdvice(new BerlinClockExceptionHandling())
                .build();
    }

    @Test
    void getSingleMinuteRow_nullOrEmptyTime_returnsBadRequest() throws Exception {
        String emptyTime = "";

        when(singleMinuteRowService.display(emptyTime))
                .thenThrow(new IllegalArgumentException("Input time cannot be null or empty"));

        mockMvc.perform(get(SINGLE_MINUTE_BASE_URL)
                        .param("time", emptyTime)
                        .accept(MediaType.APPLICATION_JSON))
     .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Input time cannot be null or empty"))
                .andExpect(jsonPath("$.path").value(SINGLE_MINUTE_BASE_URL));
    }

    @Test
    void getSingleMinuteRow_invalidFormatTime_returnsBadRequest() throws Exception {
        String invalidTime = "25:61:00";

        when(singleMinuteRowService.display(invalidTime))
                .thenThrow(new IllegalArgumentException("Invalid time format"));

        mockMvc.perform(get(SINGLE_MINUTE_BASE_URL)
                        .param("time", invalidTime)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Invalid time format"))
                .andExpect(jsonPath("$.path").value(SINGLE_MINUTE_BASE_URL));
    }

    @Test
    void getSingleMinuteRow_otherIllegalArgument_returnsBadRequest() throws Exception {
        String otherInvalidTime = "random-string";

        when(singleMinuteRowService.display(otherInvalidTime))
                .thenThrow(new IllegalArgumentException("Unexpected input"));

        mockMvc.perform(get(SINGLE_MINUTE_BASE_URL)
                        .param("time", otherInvalidTime)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Unexpected input"))
                .andExpect(jsonPath("$.path").value(SINGLE_MINUTE_BASE_URL));
    }
    @Test
    void getSingleMinuteRow_validTime_returnsOk() throws Exception {
        String validTime = "23:23:23";
        String expectedRow = "YYYO";

        when(singleMinuteRowService.display(validTime)).thenReturn(expectedRow);

        mockMvc.perform(get(SINGLE_MINUTE_BASE_URL)
                        .param("time", validTime)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedRow));
    }

    @Test
    void getSingleMinuteRow_missingRequestParam_returnsBadRequest() throws Exception {
        mockMvc.perform(get(SINGLE_MINUTE_BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Required parameter 'time' is missing"))
                .andExpect(jsonPath("$.path").value(SINGLE_MINUTE_BASE_URL));
    }


}
