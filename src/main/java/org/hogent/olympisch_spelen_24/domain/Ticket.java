package org.hogent.olympisch_spelen_24.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "tickets")
@IdClass(TicketId.class)
public class Ticket {
    @JsonBackReference
    @ManyToOne
    @Id
    private AppUser user;

    @JsonBackReference
    @ManyToOne
    @Id
    private Competition competition;

    @Range(min = 1, max = 20, message = "{ticket.count.range}")
    @NotNull(message = "{ticket.count.notNull}")
    private Long count;
}
