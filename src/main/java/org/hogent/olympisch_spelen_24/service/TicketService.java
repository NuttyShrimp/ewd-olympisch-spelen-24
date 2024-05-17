package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.Ticket;

import java.util.List;
import java.util.Map;

public interface TicketService {
    void createNew(Ticket ticket, String name);
    Map<Long, Long> getTicketCount(Long sportId, String name);
    boolean hasBoughtTickets(String name);
    List<Ticket> getAll(String name);
}
