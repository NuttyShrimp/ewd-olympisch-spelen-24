package org.hogent.olympisch_spelen_24.controller;

import org.hogent.olympisch_spelen_24.domain.Sport;
import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.hogent.olympisch_spelen_24.service.SportService;
import org.hogent.olympisch_spelen_24.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/sport")
public class SportController {
    @Autowired
    private SportService sportService;
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private TicketService ticketService;

    @GetMapping
    public String getAll(Model model) {
        Iterable<Sport> sports = sportService.getAll();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasTickets = ticketService.hasBoughtTickets(authentication.getName());

        model.addAttribute("sports", sports);
        model.addAttribute("hasTickets", hasTickets);

        return "sportList";
    }

    @GetMapping("/{id}")
    public String getCompetitions(@PathVariable Long id, Model model, Authentication authentication) {
        Sport sport = sportService.getById(id);

        Map<Long, Long> placesLeft = competitionService.getPlacesForAll(id);

        Map<Long, Long> boughtTickets = ticketService.getTicketCount(id, authentication.getName());

        model.addAttribute("placesLeft", placesLeft);
        model.addAttribute("sport", sport);
        model.addAttribute("boughtTickets", boughtTickets);

        return "competition/list";
    }
}
