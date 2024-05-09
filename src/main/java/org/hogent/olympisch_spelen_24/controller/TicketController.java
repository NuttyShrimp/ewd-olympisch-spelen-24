package org.hogent.olympisch_spelen_24.controller;

import jakarta.validation.Valid;
import org.hogent.olympisch_spelen_24.domain.*;
import org.hogent.olympisch_spelen_24.repository.CompetitionRepository;
import org.hogent.olympisch_spelen_24.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private TicketService ticketService;

    @GetMapping("/{comp_id}")
    public String showTicketBuy(@PathVariable Long comp_id, Model model){
        Optional<Competition> competition = competitionRepository.findById(comp_id);

        if (competition.isEmpty()) {
            // TODO: throw error instead of redirecting
            return "redirect:/404";
        }

        model.addAttribute("competition", competition.get());

        Ticket ticket = new Ticket();

        model.addAttribute("ticket", ticket);

        return "ticket/buy";
    }

    @PostMapping("/{comp_id}")
    public String buyTicket(@PathVariable Long comp_id, @Valid Ticket ticket, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Optional<Competition> competition = competitionRepository.findById(comp_id);

        if (competition.isEmpty()) {
            // TODO: throw error instead of redirecting
            return "redirect:/404";
        }

        if (result.hasErrors()) {
            model.addAttribute("competition", competition.get());
            return "ticket/buy";
        }
        ticketService.createNew(ticket);

        if (ticket.getCount() == 1) {
            redirectAttributes.addFlashAttribute("notification", String.format("Bought %d ticket", ticket.getCount()));
        } else {
            redirectAttributes.addFlashAttribute("notification", String.format("Bought %d tickets", ticket.getCount()));
        }

        return String.format("redirect:/sport/%d", competition.get().getSport().getId());
    }
}
