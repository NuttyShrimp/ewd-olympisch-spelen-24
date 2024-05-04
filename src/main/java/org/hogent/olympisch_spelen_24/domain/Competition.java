package org.hogent.olympisch_spelen_24.domain;

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

    @NumberFormat(pattern="#,##0.00")
    @Min(0)
    @Max(150)
    @Positive
    @NotNull
    private Double price;

    @Positive
    @Max(49)
    @NotNull
    private Long places;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Stadium stadium;

    @ManyToMany
    @Size(max = 2)
    private Set<Discipline> disciplines = new HashSet<>();
}
