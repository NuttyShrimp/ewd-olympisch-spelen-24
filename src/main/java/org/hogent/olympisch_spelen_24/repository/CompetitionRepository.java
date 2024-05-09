package org.hogent.olympisch_spelen_24.repository;

import org.hogent.olympisch_spelen_24.domain.Competition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompetitionRepository extends CrudRepository<Competition, Long> {
    List<CompetitionPlaceInfo> findCompetitionPlaceInfoBySport_Id(Long id);

    CompetitionPlaceInfo findCompetitionPlaceInfoByOlympicNr1(Long id);

    @Query("SELECT c FROM Competition c WHERE c.sport.id=:id")
    List<Competition> findBySport_Id(Long id);
}
