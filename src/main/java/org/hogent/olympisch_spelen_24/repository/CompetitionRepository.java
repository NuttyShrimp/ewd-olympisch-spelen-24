package org.hogent.olympisch_spelen_24.repository;

import org.hogent.olympisch_spelen_24.domain.Competition;
import org.springframework.data.repository.CrudRepository;

public interface CompetitionRepository extends CrudRepository<Competition, Long> {
}
