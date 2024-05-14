package org.hogent.olympisch_spelen_24.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompetitionNotFoundException extends RuntimeException {
    public CompetitionNotFoundException(Long id) {
        super(String.format("Competition with id %d not found", id));
    }
}
