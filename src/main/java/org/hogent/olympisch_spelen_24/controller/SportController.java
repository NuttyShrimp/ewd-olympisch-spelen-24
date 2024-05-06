package org.hogent.olympisch_spelen_24.controller;

import org.hogent.olympisch_spelen_24.domain.Sport;
import org.hogent.olympisch_spelen_24.repository.CompetitionRepository;
import org.hogent.olympisch_spelen_24.repository.SportRepository;
import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/sport")
public class SportController {
    @Autowired
    private SportRepository sportRepository;
    @Autowired
    private CompetitionService competitionService;

    @GetMapping
    public String getAll(Model model) {
        Iterable<Sport> sports = sportRepository.findAll();

        model.addAttribute("sports", sports);

        return "sportList";
    }

    @GetMapping("/{id}")
    public String getCompetitions(@PathVariable Long id, Model model, Authentication authentication) {
        Optional<Sport> sport = sportRepository.findById(id);

        if (sport.isEmpty()) {
            // TODO: throw error instead of redirect
            return "redirect:/404";
        }

        Map<Long, Long> placesLeft = competitionService.getPlacesLeft(id);

        model.addAttribute("placesLeft", placesLeft);
        model.addAttribute("sport", sport.get());

        return "competitionList";
    }
}
