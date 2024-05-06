package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.Ticket;
import org.hogent.olympisch_spelen_24.exceptions.MaxTicketsPerCompReached;
import org.hogent.olympisch_spelen_24.exceptions.MaxTotalTicketsReached;
import org.hogent.olympisch_spelen_24.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class TicketServiceImpl implements TicketService {
    private final long MAX_TICKET_COUNT_PER_COMP = 20;
    private final long MAX_TOTAL_TICKET_COUNT = 100;

    @Autowired
    private TicketRepository ticketRepository;

    public void createNew(Ticket newTicket) {
        Iterable<Ticket> tickets = ticketRepository.findByUser_IdAndCompetition_OlympicNr1(newTicket.getUser().getId(), newTicket.getCompetition().getOlympicNr1());
        long ticketCount = 0;
        for(Ticket ticket : tickets) {
            ticketCount += ticket.getCount();
        }
        if (ticketCount > MAX_TICKET_COUNT_PER_COMP) {
            throw new MaxTicketsPerCompReached();
        }

        tickets = ticketRepository.findByUser_Id(newTicket.getUser().getId());
        ticketCount = 0;
        for(Ticket ticket : tickets) {
            ticketCount += ticket.getCount();
        }
        if (ticketCount > MAX_TOTAL_TICKET_COUNT) {
            throw new MaxTotalTicketsReached();
        }

        ticketRepository.save(newTicket);
    }
}
