package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.Competition;

import java.util.List;
import java.util.Map;

public interface CompetitionService {
    List<Competition> getCompetitionsForSport(Long sportId);
    Competition getById(Long id);
    void saveNew(Competition competition);
    // Get for each competition the places/tickets left
    Map<Long, Long> getPlacesForAll(Long sportId);
    Long getPlaces(Long competitionId);
}
