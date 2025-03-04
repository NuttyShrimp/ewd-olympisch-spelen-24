package org.hogent.olympisch_spelen_24.controller;

import jakarta.validation.Valid;
import org.hogent.olympisch_spelen_24.domain.Competition;
import org.hogent.olympisch_spelen_24.domain.Discipline;
import org.hogent.olympisch_spelen_24.domain.Sport;
import org.hogent.olympisch_spelen_24.domain.Stadium;
import org.hogent.olympisch_spelen_24.repository.CompetitionRepository;
import org.hogent.olympisch_spelen_24.repository.DisciplineRepository;
import org.hogent.olympisch_spelen_24.repository.SportRepository;
import org.hogent.olympisch_spelen_24.repository.StadiumRepository;
import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.hogent.olympisch_spelen_24.service.SportService;
import org.hogent.olympisch_spelen_24.utils.CompetitionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/competition")
public class CompetionController {
    @Autowired
    SportService sportService;
    @Autowired
    CompetitionRepository competitionRepository;
    @Autowired
    StadiumRepository stadiumRepository;
    @Autowired
    DisciplineRepository disciplineRepository;
    @Autowired
    CompetitionValidation competitionValidation;
    @Autowired
    CompetitionService competitionService;

    @GetMapping("/{sport_id}/new")
    public String newCompetion(@PathVariable("sport_id") long sportId, Model model) {
        Sport sport = sportService.getById(sportId);

        Competition competition = new Competition();
        competition.setSport(sport);

        Iterable<Stadium> stadiums = stadiumRepository.findAll();
        model.addAttribute("stadiums", stadiums);

        Iterable<Discipline> disciplines = disciplineRepository.findBySport_Id(sportId);
        model.addAttribute("disciplines", disciplines);

        model.addAttribute("competition", competition);

        return "competition/new";
    }

    @PostMapping("/{sport_id}/new")
    public String newCompetion(@PathVariable("sport_id") long sportId, @Valid Competition competition, BindingResult result, Model model) {
        competitionValidation.validate(competition, result);

        if (result.hasErrors()) {
            // Check if sport exists
            sportService.getById(sportId);

            Iterable<Stadium> stadiums = stadiumRepository.findAll();
            model.addAttribute("stadiums", stadiums);

            Iterable<Discipline> disciplines = disciplineRepository.findBySport_Id(sportId);
            model.addAttribute("disciplines", disciplines);
            return "competition/new";
        }

        competitionService.saveNew(competition);

        return "competition/success";
    }
}
