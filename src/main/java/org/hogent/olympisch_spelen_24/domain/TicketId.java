package org.hogent.olympisch_spelen_24.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TicketId implements Serializable {
    private AppUser user;
    private Competition competition;
    public TicketId(AppUser user, Competition competition) {
        this.user = user;
        this.competition = competition;
    }
}
