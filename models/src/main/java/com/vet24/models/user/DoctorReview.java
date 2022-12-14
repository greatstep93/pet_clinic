package com.vet24.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class DoctorReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(targetEntity = Comment.class, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Comment comment;
    @ManyToOne(targetEntity = Doctor.class)
    private Doctor doctor;

    public DoctorReview(Comment comment, Doctor doctor) {
        this.comment = comment;
        this.doctor = doctor;
    }
}
