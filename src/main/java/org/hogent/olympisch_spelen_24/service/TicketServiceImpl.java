package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.AppUser;
import org.hogent.olympisch_spelen_24.domain.Ticket;
import org.hogent.olympisch_spelen_24.exceptions.MaxTicketsPerCompReached;
import org.hogent.olympisch_spelen_24.exceptions.MaxTotalTicketsReached;
import org.hogent.olympisch_spelen_24.exceptions.UserNotFoundException;
import org.hogent.olympisch_spelen_24.repository.TicketRepository;
import org.hogent.olympisch_spelen_24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {
    private final long MAX_TICKET_COUNT_PER_COMP = 20;
    private final long MAX_TOTAL_TICKET_COUNT = 100;

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    public void createNew(Ticket newTicket, String username) throws UserNotFoundException, MaxTicketsPerCompReached, MaxTotalTicketsReached {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        newTicket.setUser(user);

        Iterable<Ticket> tickets = ticketRepository.findByUser_IdAndCompetition_OlympicNr1(newTicket.getUser().getId(), newTicket.getCompetition().getOlympicNr1());
        long ticketCount = 0;
        for (Ticket ticket : tickets) {
            ticketCount += ticket.getCount();
        }
        if (ticketCount > MAX_TICKET_COUNT_PER_COMP) {
            throw new MaxTicketsPerCompReached();
        }

        tickets = ticketRepository.findByUser_Id(newTicket.getUser().getId());
        ticketCount = 0;
        for (Ticket ticket : tickets) {
            ticketCount += ticket.getCount();
        }
        if (ticketCount > MAX_TOTAL_TICKET_COUNT) {
            throw new MaxTotalTicketsReached();
        }

        ticketRepository.save(newTicket);
    }
}
