package org.hogent.olympisch_spelen_24.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "competitions")
public class Competition {
    @Id
    @NotNull
    @NumberFormat(pattern = "#####")
    private Long olympicNr1;

    @NotNull
    @NumberFormat(pattern = "#####")
    private Long olympicNr2;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime time;

    @Min(0)
    @Max(150)
    @Positive
    @NotNull
    private Double price;

    @Positive
    @Max(49)
    @NotNull
    private Long places;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Sport sport;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Stadium stadium;

    @JsonManagedReference
    @ManyToMany
    @Size(max = 2)
    private Set<Discipline> disciplines = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "competition")
    private Set<Ticket> tickets = new HashSet<>();

    public Competition(Long olympicNr1, Long olympicNr2, LocalDateTime time, Double price, Long places, Sport sport, Stadium stadium) {
        this.olympicNr1 = olympicNr1;
        this.olympicNr2 = olympicNr2;
        this.time = time;
        this.price = price;
        this.places = places;
        this.sport = sport;
        this.stadium = stadium;
    }

    public String getDisciplineNames() {
        StringBuilder sb = new StringBuilder();
        disciplines.forEach(discipline -> sb.append(discipline.getName()).append(", "));
        if (!sb.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length() - 1);
        }
        return sb.toString();
    }
}
