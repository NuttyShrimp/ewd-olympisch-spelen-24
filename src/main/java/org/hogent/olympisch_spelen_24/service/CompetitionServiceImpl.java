package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.Competition;
import org.hogent.olympisch_spelen_24.exceptions.CompetitionNotFoundException;
import org.hogent.olympisch_spelen_24.exceptions.DuplicateCompetitionException;
import org.hogent.olympisch_spelen_24.repository.CompetitionPlaceInfo;
import org.hogent.olympisch_spelen_24.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    CompetitionRepository competitionRepository;


    public List<Competition> getCompetitionsForSport(Long sportId) {
        return competitionRepository.findBySport_Id(sportId).stream().toList();
    }

    public Competition getById(Long id) {
        Optional<Competition> competition = competitionRepository.findById(id);
        if (competition.isEmpty()) {
            throw new CompetitionNotFoundException(id);
        }
        return competition.get();
    }

    @Override
    public void saveNew(Competition competition) {
        if (competitionRepository.existsById(competition.getOlympicNr1())) {
            throw new DuplicateCompetitionException(competition.getOlympicNr1());
        }
        competitionRepository.save(competition);
    }

    public Map<Long, Long> getPlacesForAll(Long sportId) {
        List<CompetitionPlaceInfo> placeInfoList = competitionRepository.findCompetitionPlaceInfoBySport_Id(sportId);
        Map<Long, Long> placesLeft = new HashMap<>();
        for (CompetitionPlaceInfo placeInfo : placeInfoList) {
            placesLeft.put(placeInfo.getOlympicNr1(), placeInfo.getPlaces() - placeInfo.getTickets().size());
        }
        return placesLeft;
    }

    public Long getPlaces(Long competitionId) {
        CompetitionPlaceInfo placeInfo = competitionRepository.findCompetitionPlaceInfoByOlympicNr1(competitionId);
        return placeInfo.getPlaces() - placeInfo.getTickets().size();
    }
}
