package org.hogent.olympisch_spelen_24.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "disciplines")
public class Discipline {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  private Sport sport;

  @ManyToMany
  private Set<Competition> competitions = new HashSet<>();

  public Discipline(String name, Sport sport) {
    this.name = name;
    this.sport = sport;
  }
}