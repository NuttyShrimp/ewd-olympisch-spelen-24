package org.hogent.olympisch_spelen_24.repository;

import org.hogent.olympisch_spelen_24.domain.Competition;
import org.hogent.olympisch_spelen_24.domain.Discipline;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DisciplineRepository extends CrudRepository<Discipline, Long> {
    List<Discipline> findBySport_Id(Long id);
}
