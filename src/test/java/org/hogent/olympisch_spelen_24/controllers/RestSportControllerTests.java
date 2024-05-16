package org.hogent.olympisch_spelen_24.controllers;


import org.hogent.olympisch_spelen_24.config.SecurityConfig;
import org.hogent.olympisch_spelen_24.domain.Competition;
import org.hogent.olympisch_spelen_24.domain.Sport;
import org.hogent.olympisch_spelen_24.domain.Stadium;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({SecurityConfig.class, MockitoExtension.class})
class RestSportControllerTests {
    @MockBean
    CompetitionService competitionService;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        Sport sport = new Sport("Golf");
        Stadium stadium = new Stadium("Test stadium");
        Competition comp = Competition.builder().olympicNr1(12345L).olympicNr2(12355L).price(10.0).places(30L).time(LocalDateTime.of(2024, 8, 2, 11, 30)).sport(sport).stadium(stadium).build();
        when(competitionService.getCompetitionsForSport(12345L)).thenReturn(List.of(comp));
    }

    @Test
    public void testGetCompetitionsForSport() throws Exception {
        mockMvc.perform(get("/api/sport/12345"))
                .andExpect(status().isOk());
    }
}
