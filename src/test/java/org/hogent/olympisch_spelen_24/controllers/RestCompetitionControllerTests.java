package org.hogent.olympisch_spelen_24.controllers;

import org.hogent.olympisch_spelen_24.config.SecurityConfig;
import org.hogent.olympisch_spelen_24.exceptions.CompetitionNotFoundException;
import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({SecurityConfig.class, MockitoExtension.class})
class RestCompetitionControllerTests {
    @MockBean
    CompetitionService competitionService;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        when(competitionService.getPlaces(12345L)).thenReturn(10L);
        when(competitionService.getPlaces(12955L)).thenThrow(CompetitionNotFoundException.class);
    }

    @Test
    void testGetCompetitionPlaces() throws Exception {
        mockMvc.perform(get("/api/competition/12345/places"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUnknownCompetitionPlaces() throws Exception {
        mockMvc.perform(get("/api/competition/12955/places"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(CompetitionNotFoundException.class, result.getResolvedException()));
    }
}
