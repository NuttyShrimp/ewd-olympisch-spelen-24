package org.hogent.olympisch_spelen_24.repository;

import org.hogent.olympisch_spelen_24.domain.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findByUser_Id(Long id);

    List<Ticket> findByUser_IdAndCompetition_OlympicNr1(Long id, Long olympicNr1);
}
