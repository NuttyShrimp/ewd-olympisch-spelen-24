package org.hogent.olympisch_spelen_24.controllers;

import org.hogent.olympisch_spelen_24.config.SecurityConfig;
import org.hogent.olympisch_spelen_24.controller.SportController;
import org.hogent.olympisch_spelen_24.domain.Sport;
import org.hogent.olympisch_spelen_24.exceptions.SportNotFoundException;
import org.hogent.olympisch_spelen_24.repository.SportRepository;
import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.hogent.olympisch_spelen_24.service.SportService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({MockitoExtension.class, SecurityConfig.class})
@WebMvcTest(SportController.class)
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
class SportControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    SportService sportService;

    @MockBean
    CompetitionService competitionService;

    @Test
    @WithMockUser(username = "user")
    void testGetAllSports_withUser_expectList() throws Exception {
        mockMvc.perform(get("/sport"))
                .andExpect(status().isOk())
                .andExpect(view().name("sportList"))
                .andExpect(model().attributeExists("sports"));
    }

    @Test
    void testGetAllSports_Unauthorized_shouldRedirect() throws Exception {
        mockMvc.perform(get("/sport"))
                .andExpect(status().is3xxRedirection())
                // /login is a special endpoint when it comes to redirect + testing (https://stackoverflow.com/questions/78105328/spring-security-mock-mvc-testing-assertion-error-redirect-url-http-local)
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "user")
    void testGetSpecificSport_withUser_isFound() throws Exception {
        Sport sport = new Sport("Golf");
        when(sportService.getById(1L)).thenReturn(sport);

        mockMvc.perform(get("/sport/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("competitionList"))
                .andExpect(model().attributeExists("sport"))
                .andExpect(model().attribute("sport", sport));
    }


    @Test
    @WithMockUser(username = "user")
    void testGetUnknownSport_withUser_shouldRedirect() throws Exception {
        when(sportService.getById(1L)).thenThrow(SportNotFoundException.class);

        mockMvc.perform(get("/sport/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(SportNotFoundException.class, result.getResolvedException()));
    }
}
