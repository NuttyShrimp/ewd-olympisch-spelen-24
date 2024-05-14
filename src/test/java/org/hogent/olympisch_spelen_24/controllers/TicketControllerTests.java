package org.hogent.olympisch_spelen_24.controllers;


import org.hogent.olympisch_spelen_24.config.SecurityConfig;
import org.hogent.olympisch_spelen_24.controller.TicketController;
import org.hogent.olympisch_spelen_24.domain.Competition;
import org.hogent.olympisch_spelen_24.domain.Sport;
import org.hogent.olympisch_spelen_24.domain.Stadium;
import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.hogent.olympisch_spelen_24.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({MockitoExtension.class, SecurityConfig.class})
@WebMvcTest(TicketController.class)
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
public class TicketControllerTests {
    private final Sport sport = new Sport("Golf");
    private final Stadium stadium = new Stadium("Test stadium");
    private final Competition competition = Competition.builder().olympicNr1(12345L).olympicNr2(12355L).price(10.0).places(30L).time(LocalDateTime.of(2024, 8, 2, 11, 30)).sport(sport).stadium(stadium).build();
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private CompetitionService competitionService;
    @MockBean
    private TicketService ticketService;

    @BeforeEach
    void addCompetition() {
        sport.setId(1L);
        when(competitionService.getById(1L)).thenReturn(competition);
    }

    @Test
    @WithMockUser(username = "user")
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsService")
    void testShowTicketBuy_asUser_shouldShow() throws Exception {
        mockMvc.perform(get("/ticket/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("competition"))
                .andExpect(model().attributeExists("ticket"));
    }
}
