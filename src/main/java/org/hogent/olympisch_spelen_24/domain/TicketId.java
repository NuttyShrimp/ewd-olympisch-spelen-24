package org.hogent.olympisch_spelen_24.domain;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class TicketId implements Serializable {
    private User user;
    private Competition competition;
    public TicketId(User user, Competition competition) {
        this.user = user;
        this.competition = competition;
    }
}
