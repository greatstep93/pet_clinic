package com.vet24.models.pet.clinicalexamination;

import com.vet24.models.pet.Pet;
import com.vet24.models.user.Doctor;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "clinical_examination")
public class ClinicalExamination implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @Column(name = "date")
    private LocalDate date;

    @NonNull
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Pet pet;

    @NonNull
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Doctor doctor;

    @NonNull
    @Column
    private Double weight; //вес, при том последний вес описывается в таблицу животного

    @Column
    private Boolean isCanMove; // животное здорово и выдержит перевозку до места назначения? true/false

    @Column
    private String text; // общее описание состояния животного

    public ClinicalExamination(LocalDate date,
                               Pet pet,
                               Doctor doctor,
                               Double weight,
                               Boolean isCanMove, String text) {
        this.date = date;
        this.pet = pet;
        this.doctor = doctor;
        this.weight = weight;
        this.isCanMove = isCanMove;
        this.text = text;
    }
}
