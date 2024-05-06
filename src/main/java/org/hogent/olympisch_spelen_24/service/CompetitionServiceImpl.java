package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.Competition;
import org.hogent.olympisch_spelen_24.exceptions.DuplicateCompetitionException;
import org.hogent.olympisch_spelen_24.repository.CompetitionPlaceInfo;
import org.hogent.olympisch_spelen_24.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Long, Long> getPlacesLeft(Long sportId) {
        List<CompetitionPlaceInfo> placeInfoList = competitionRepository.findBySport_Id(sportId);
        Map<Long, Long> placesLeft = new HashMap<>();
        for(CompetitionPlaceInfo placeInfo : placeInfoList) {
            placesLeft.put(placeInfo.getOlympicNr1(), placeInfo.getPlaces() - placeInfo.getTickets().size());
        }
        return placesLeft;
    }
}
