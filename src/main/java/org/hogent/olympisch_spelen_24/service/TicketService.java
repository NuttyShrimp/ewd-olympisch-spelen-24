package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.Ticket;

public interface TicketService {
    void createNew(Ticket ticket, String name);
}
