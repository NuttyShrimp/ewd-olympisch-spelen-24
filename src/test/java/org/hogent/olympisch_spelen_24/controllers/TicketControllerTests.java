package org.hogent.olympisch_spelen_24.controllers;


import org.hogent.olympisch_spelen_24.config.SecurityConfig;
import org.hogent.olympisch_spelen_24.controller.TicketController;
import org.hogent.olympisch_spelen_24.domain.*;
import org.hogent.olympisch_spelen_24.repository.UserRepository;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({MockitoExtension.class, SecurityConfig.class})
@WebMvcTest(TicketController.class)
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
class TicketControllerTests {
    private final Sport sport = new Sport("Golf");
    private final Stadium stadium = new Stadium("Test stadium");
    private final Competition competition = Competition.builder().olympicNr1(12345L).olympicNr2(12355L).price(10.0).places(30L).time(LocalDateTime.of(2024, 8, 2, 11, 30)).sport(sport).stadium(stadium).build();
    private final AppUser user = AppUser.builder().username("user").password("password").build();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private CompetitionService competitionService;
    @MockBean
    private TicketService ticketService;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void addCompetition() {
        sport.setId(1L);
        when(competitionService.getById(1L)).thenReturn(competition);
    }

    @Test
    @WithMockUser(username = "user")
    void testShowTicketBuy_asUser_shouldShow() throws Exception {
        mockMvc.perform(get("/ticket/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("competition"))
                .andExpect(model().attributeExists("ticket"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testShowTicketBuy_asAdmin_isForbidden() throws Exception {
        mockMvc.perform(get("/ticket/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user")
    void testBuyTicket_validTickets_shouldSucceed() throws Exception {
        Ticket ticket = Ticket.builder().count(10L).build();
        mockMvc.perform(post("/ticket/1").flashAttr("ticket", ticket).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sport/1"))
                .andExpect(flash().attributeExists("notification"))
                .andExpect(flash().attribute("notification", "Bought 10 tickets"));
        verify(ticketService, times(1)).createNew(ticket, "user");
    }

    @Test
    @WithMockUser(username = "user")
    void testBuyTicket_validTicket_shouldSucceed() throws Exception {
        Ticket ticket = Ticket.builder().count(1L).build();
        mockMvc.perform(post("/ticket/1").flashAttr("ticket", ticket).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sport/1"))
                .andExpect(flash().attributeExists("notification"))
                .andExpect(flash().attribute("notification", "Bought 1 ticket"));
        verify(ticketService, times(1)).createNew(ticket, "user");
    }

    @Test
    @WithMockUser(username = "user")
    void testBuyTicket_negativeAmount_shouldGiveErrors() throws Exception {
        Ticket ticket = Ticket.builder().count(-1L).build();
        mockMvc.perform(post("/ticket/1").flashAttr("ticket", ticket).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("ticket"))
                .andExpect(model().attributeHasFieldErrors("ticket", "count"));
    }

    @Test
    @WithMockUser(username = "user")
    void testBuyTicket_zeroAmount_shouldGiveErrors() throws Exception {
        Ticket ticket = Ticket.builder().count(0L).build();
        mockMvc.perform(post("/ticket/1").flashAttr("ticket", ticket).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("ticket"))
                .andExpect(model().attributeHasFieldErrors("ticket", "count"));
    }

    @Test
    @WithMockUser(username = "user")
    void testBuyTicket_amountToHigh_shouldGiveErrors() throws Exception {
        Ticket ticket = Ticket.builder().count(90L).build();
        mockMvc.perform(post("/ticket/1").flashAttr("ticket", ticket).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("ticket"))
                .andExpect(model().attributeHasFieldErrors("ticket", "count"));
    }
}
