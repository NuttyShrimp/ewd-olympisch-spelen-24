package org.hogent.olympisch_spelen_24.repository;

import org.hogent.olympisch_spelen_24.domain.Discipline;
import org.hogent.olympisch_spelen_24.domain.Stadium;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StadiumRepository extends CrudRepository<Stadium, Long> {
}
