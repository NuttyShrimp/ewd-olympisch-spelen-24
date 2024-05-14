package org.hogent.olympisch_spelen_24.controllers;

import org.hogent.olympisch_spelen_24.config.SecurityConfig;
import org.hogent.olympisch_spelen_24.controller.CompetionController;
import org.hogent.olympisch_spelen_24.domain.Competition;
import org.hogent.olympisch_spelen_24.domain.Sport;
import org.hogent.olympisch_spelen_24.domain.Stadium;
import org.hogent.olympisch_spelen_24.repository.CompetitionRepository;
import org.hogent.olympisch_spelen_24.repository.DisciplineRepository;
import org.hogent.olympisch_spelen_24.repository.StadiumRepository;
import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.hogent.olympisch_spelen_24.service.SportService;
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

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({MockitoExtension.class, SecurityConfig.class})
@WebMvcTest(CompetionController.class)
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
class CompetitionControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    SportService sportService;
    @MockBean
    CompetitionRepository competitionRepository;
    @MockBean
    StadiumRepository stadiumRepository;
    @MockBean
    DisciplineRepository disciplineRepository;
    @MockBean
   CompetitionService competitionService;

    private final Sport sport = new Sport("Golf");
    private final Stadium stadium = new Stadium("Test stadium");

    @BeforeEach
    void createSport() {
        sport.setId(1L);
        when(sportService.getById(1)).thenReturn(sport);
    }

    private void testInvalidOlympicNr1(Long olympicNr1) throws Exception {
        Competition comp = Competition.builder().olympicNr1(olympicNr1).olympicNr2(12355L).price(10.0).places(30L).time(LocalDateTime.of(2024, 8, 2, 11, 30)).sport(sport).stadium(stadium).build();
        mockMvc.perform(post("/competition/1/new").flashAttr("competition", comp).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("competition"))
                .andExpect(model().attributeHasFieldErrors("competition", "olympicNr1"));
    }

    private void testInvalidOlympicNr2(Long olympicNr2) throws Exception {
        Competition comp = Competition.builder().olympicNr1(12345L).olympicNr2(olympicNr2).price(10.0).places(30L).time(LocalDateTime.of(2024, 8, 2, 11, 30)).sport(sport).stadium(stadium).build();
        mockMvc.perform(post("/competition/1/new").flashAttr("competition", comp).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("competition"))
                .andExpect(model().attributeHasFieldErrors("competition", "olympicNr2"));
    }

    private void testInvalidPrice(Double price) throws Exception {
        Competition comp = Competition.builder().olympicNr1(12345L).olympicNr2(12355L).price(price).places(30L).time(LocalDateTime.of(2024, 8, 2, 11, 30)).sport(sport).stadium(stadium).build();
        mockMvc.perform(post("/competition/1/new").flashAttr("competition", comp).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("competition"))
                .andExpect(model().attributeHasFieldErrors("competition", "price"));
    }

    private void testInvalidDate(LocalDateTime dateTime) throws Exception {
        Competition comp = Competition.builder().olympicNr1(12345L).olympicNr2(12355L).price(10.0).places(30L).time(dateTime).sport(sport).stadium(stadium).build();
        mockMvc.perform(post("/competition/1/new").flashAttr("competition", comp).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("competition"))
                .andExpect(model().attributeHasFieldErrors("competition", "time"));
    }

    private void testInvalidPlaces(Long places) throws Exception {
        Competition comp = Competition.builder().olympicNr1(12345L).olympicNr2(12355L).price(10.0).places(places).time(LocalDateTime.of(2024, 8, 2, 11, 30)).sport(sport).stadium(stadium).build();
        mockMvc.perform(post("/competition/1/new").flashAttr("competition", comp).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("competition"))
                .andExpect(model().attributeHasFieldErrors("competition", "places"));
    }

    @Test
    @WithMockUser(username = "user")
    void testNewCompetition_asUser_isForbidden() throws Exception {
        mockMvc.perform(get("/competition/1/new"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testNewCompetition_asAdmin_shouldLoad() throws Exception {
        mockMvc.perform(get("/competition/1/new"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_validCompetition_shouldSucceed() throws Exception {
        Competition comp = Competition.builder().olympicNr1(12345L).olympicNr2(12355L).price(10.0).places(30L).time(LocalDateTime.of(2024, 8, 2, 11, 30)).sport(sport).stadium(stadium).build();
        mockMvc.perform(post("/competition/1/new").flashAttr("competition", comp).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("competition/success"));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_OlympicNr1ToSmall_shouldGiveErrors() throws Exception {
        testInvalidOlympicNr1(2345L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_OlympicNr1ToHigh_shouldGiveErrors() throws Exception {
        testInvalidOlympicNr1(112345L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_missingOlympicNr1_shouldGiveErrors() throws Exception {
        testInvalidOlympicNr1(null);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_OlympicNr1sameStartEnd_shouldGiveErrors() throws Exception {
        testInvalidOlympicNr1(12341L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_OlympicNr2ToLow_shouldGiveErrors() throws Exception {
        testInvalidOlympicNr2(9345L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_OlympicNr2ToHigh_shouldGiveErrors() throws Exception {
        testInvalidOlympicNr2(22345L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_OlympicNr2Missing_shouldGiveErrors() throws Exception {
        testInvalidOlympicNr2(null);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_NegativePrice_shouldGiveErrors() throws Exception {
        testInvalidPrice(-1.0);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_ZeroPrice_shouldGiveErrors() throws Exception {
        testInvalidPrice(0.0);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_PriceToHigh_shouldGiveErrors() throws Exception {
        testInvalidPrice(200.0);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_DateToEarly_shouldGiveErrors() throws Exception {
        testInvalidDate(LocalDateTime.of(2024, 7, 25, 11, 30));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_DateToLate_shouldGiveErrors() throws Exception {
        testInvalidDate(LocalDateTime.of(2024, 8, 12, 11, 30));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_DateTimeToEarly_shouldGiveErrors() throws Exception {
        testInvalidDate(LocalDateTime.of(2024, 7, 28, 6, 30));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_DateTimeToLate_shouldGiveErrors() throws Exception {
        testInvalidDate(LocalDateTime.of(2024, 7, 28, 18, 30));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_DateTimeMissing_shouldGiveErrors() throws Exception {
        testInvalidDate(null);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_negativePlaces_shouldGiveErrors() throws Exception {
        testInvalidPlaces(-1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_zeroPlaces_shouldGiveErrors() throws Exception {
        testInvalidPlaces(0L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_placesToHigh_shouldGiveErrors() throws Exception {
        testInvalidPlaces(100L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCompetition_placesMissing_shouldGiveErrors() throws Exception {
        testInvalidPlaces(null);
    }
}
