package org.hogent.olympisch_spelen_24.exceptions;

public class DuplicateCompetitionException extends RuntimeException {
    public DuplicateCompetitionException(long olympicNr1) {
        super(String.format("Duplicate competition olympicNr1:%d", olympicNr1));
    }
}
