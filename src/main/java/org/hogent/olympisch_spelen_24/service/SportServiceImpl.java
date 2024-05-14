package org.hogent.olympisch_spelen_24.service;

import org.hogent.olympisch_spelen_24.domain.Sport;
import org.hogent.olympisch_spelen_24.exceptions.SportNotFoundException;
import org.hogent.olympisch_spelen_24.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SportServiceImpl implements SportService {
    @Autowired
    SportRepository sportRepository;

    @Override
    public Sport getById(long id) throws SportNotFoundException {
        Optional<Sport> sport = sportRepository.findById(id);

        if (sport.isEmpty()) {
            throw new SportNotFoundException(id);
        }

        return sport.get();
    }

    @Override
    public Iterable<Sport> getAll() {
        return sportRepository.findAll();
    }
}
