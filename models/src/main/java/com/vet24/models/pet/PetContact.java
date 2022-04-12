package com.vet24.models.pet;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PetContact {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    private String ownerName;

    @NonNull
    private String address;

    @NonNull
    @Column
    private Long phone;

    @NonNull
    @Column(unique = true)
    private String petCode;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Pet pet;
}