package org.hogent.olympisch_spelen_24.controller;

import jakarta.validation.Valid;
import org.hogent.olympisch_spelen_24.domain.*;
import org.hogent.olympisch_spelen_24.repository.CompetitionRepository;
import org.hogent.olympisch_spelen_24.service.CompetitionService;
import org.hogent.olympisch_spelen_24.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private TicketService ticketService;

    @GetMapping("")
    public String showAllTickets(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Ticket> tickets = ticketService.getAll(auth.getName());

        if (tickets.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("tickets", tickets);

        return "ticket/index";
    }

    @GetMapping("/{comp_id}")
    public String showTicketBuy(@PathVariable Long comp_id, Model model){
        Competition competition = competitionService.getById(comp_id);
        model.addAttribute("competition", competition);

        Ticket ticket = new Ticket();
        model.addAttribute("ticket", ticket);

        return "ticket/buy";
    }

    @PostMapping("/{comp_id}")
    public String buyTicket(@PathVariable Long comp_id, @Valid Ticket ticket, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Competition competition = competitionService.getById(comp_id);
        if (result.hasErrors()) {
            model.addAttribute("competition", competition);
            return "ticket/buy";
        }
        ticket.setCompetition(competition);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ticketService.createNew(ticket, auth.getName());

        if (ticket.getCount() == 1) {
            redirectAttributes.addFlashAttribute("notification", String.format("Bought %d ticket", ticket.getCount()));
        } else {
            redirectAttributes.addFlashAttribute("notification", String.format("Bought %d tickets", ticket.getCount()));
        }

        return String.format("redirect:/sport/%d", competition.getSport().getId());
    }
}
