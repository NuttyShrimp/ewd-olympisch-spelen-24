package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.Sport;

public interface SportService {
    Sport getById(long id);

    Iterable<Sport> getAll();
}
