package org.hogent.olympisch_spelen_24.repository;

import org.hogent.olympisch_spelen_24.domain.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findByUser_Id(Long id);

    List<Ticket> findByUser_IdAndCompetition_OlympicNr1(Long id, Long olympicNr1);

    boolean existsByUser_Username(String name);

    List<Ticket> findByUser_UsernameAndCompetition_Sport_Id(String username, Long id);
}
