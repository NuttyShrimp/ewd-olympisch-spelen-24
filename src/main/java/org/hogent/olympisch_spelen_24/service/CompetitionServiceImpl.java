package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.Competition;
import org.hogent.olympisch_spelen_24.exceptions.DuplicateCompetitionException;
import org.hogent.olympisch_spelen_24.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    CompetitionRepository competitionRepository;

    @Override
    public void saveNew(Competition competition) {
        if (competitionRepository.existsById(competition.getOlympicNr1())) {
            throw new DuplicateCompetitionException(competition.getOlympicNr1());
        }
        competitionRepository.save(competition);
    }
}
