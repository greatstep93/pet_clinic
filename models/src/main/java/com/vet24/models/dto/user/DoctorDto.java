package com.vet24.models.dto.user;


import com.vet24.models.dto.medicine.DiagnosisDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = "diagnoses")
public class DoctorDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String avatar;
    private String email;
    private String password;
    private Set<DiagnosisDto> diagnoses;
}
