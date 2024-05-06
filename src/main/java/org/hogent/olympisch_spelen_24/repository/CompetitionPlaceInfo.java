package org.hogent.olympisch_spelen_24.repository;

import org.hogent.olympisch_spelen_24.domain.Ticket;

import java.util.Set;

/**
 * Projection for {@link org.hogent.olympisch_spelen_24.domain.Competition}
 */
public interface CompetitionPlaceInfo {
    Long getOlympicNr1();

    Long getPlaces();

    Set<Ticket> getTickets();
}