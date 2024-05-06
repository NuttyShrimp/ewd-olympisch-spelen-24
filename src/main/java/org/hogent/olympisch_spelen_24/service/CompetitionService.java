package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.Competition;

import java.util.Map;

public interface CompetitionService {
    void saveNew(Competition competition);
    // Get for each competition the places/tickets left
    Map<Long, Long> getPlacesLeft(Long sportId);
}
