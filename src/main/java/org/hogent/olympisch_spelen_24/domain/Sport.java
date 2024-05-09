package org.hogent.olympisch_spelen_24.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sports")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "id")
public class Sport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "sport", fetch = FetchType.LAZY)
    private Set<Discipline> disciplines;

    @JsonManagedReference
    @OneToMany(mappedBy = "sport", fetch = FetchType.LAZY)
    private Set<Competition> competitions;

    public Sport(String name) {
        this.name = name;
    }
}