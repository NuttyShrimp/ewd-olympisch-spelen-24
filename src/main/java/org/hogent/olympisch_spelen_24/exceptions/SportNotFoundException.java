package org.hogent.olympisch_spelen_24.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SportNotFoundException extends RuntimeException {
    public SportNotFoundException(long id) {
        super(String.format("Sport with id %d not found", id));
    }
}
