package org.hogent.olympisch_spelen_24.controller;

import org.hogent.olympisch_spelen_24.domain.Sport;
import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.hogent.olympisch_spelen_24.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/sport")
public class SportController {
    @Autowired
    private SportService sportService;
    @Autowired
    private CompetitionService competitionService;

    @GetMapping
    public String getAll(Model model) {
        Iterable<Sport> sports = sportService.getAll();

        model.addAttribute("sports", sports);

        return "sportList";
    }

    @GetMapping("/{id}")
    public String getCompetitions(@PathVariable Long id, Model model, Authentication authentication) {
        Sport sport = sportService.getById(id);

        Map<Long, Long> placesLeft = competitionService.getPlacesForAll(id);

        model.addAttribute("placesLeft", placesLeft);
        model.addAttribute("sport", sport);

        return "competitionList";
    }
}
