package org.hogent.olympisch_spelen_24.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
@IdClass(TicketId.class)
public class Ticket {
    @JsonBackReference
    @ManyToOne
    @NotNull
    @Id
    private User user;

    @JsonBackReference
    @ManyToOne
    @NotNull
    @Id
    private Competition competition;

    @Min(1)
    @Max(20)
    @NotNull
    private Long count;
}
