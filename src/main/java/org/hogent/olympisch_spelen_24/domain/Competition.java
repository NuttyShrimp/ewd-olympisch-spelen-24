package org.hogent.olympisch_spelen_24.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "competitions")
public class Competition {
    @Id
    @NotNull
    @NumberFormat(pattern = "#####")
    @Range(min = 10000, max = 99999)
    private Long olympicNr1;

    @NotNull
    @NumberFormat(pattern = "#####")
    @Range(min = 9000, max = 100999)
    private Long olympicNr2;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime time;

    @Range(min = 1, max = 149)
    @NotNull
    private Double price;

    @Range(min = 1, max = 49)
    @NotNull
    private Long places;

    @JsonBackReference
    @ManyToOne
    @NotNull
    private Sport sport;

    @JsonManagedReference
    @ManyToOne
    @NotNull
    private Stadium stadium;

    @JsonManagedReference
    @Builder.Default
    @ManyToMany
    @Size(max = 2)
    @UniqueElements
    private Set<Discipline> disciplines = new HashSet<>();

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "competition", cascade = CascadeType.REMOVE)
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
