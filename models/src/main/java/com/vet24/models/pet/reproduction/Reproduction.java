package com.vet24.models.pet.reproduction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Reproduction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate estrusStart;

    @Column
    private LocalDate mating;

    @Column
    private LocalDate dueDate;

    @Column
    private Integer childCount;

    public Reproduction(LocalDate estrusStart, LocalDate mating, LocalDate dueDate, Integer childCount) {
        this.estrusStart = estrusStart;
        this.mating = mating;
        this.dueDate = dueDate;
        this.childCount = childCount;
    }
}
