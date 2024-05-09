package org.hogent.olympisch_spelen_24.controller;

import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/competition")
public class RestCompetitionController {
    @Autowired
    CompetitionService competitionService;

    @GetMapping("/{comp_id}/places")
    public Long getCompetitionPlaces(@PathVariable("comp_id") long compId) {
        return competitionService.getPlaces(compId);
    }
}
