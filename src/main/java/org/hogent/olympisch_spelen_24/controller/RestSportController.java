package org.hogent.olympisch_spelen_24.controller;

import org.hogent.olympisch_spelen_24.domain.Competition;
import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sport")
public class RestSportController {
    @Autowired
    CompetitionService competitionService;

    @GetMapping("/{sport_id}")
    public List<Competition> getCompetitions(@PathVariable("sport_id") long sportId) {
        return competitionService.getCompetitionsForSport(sportId);
    }
}
