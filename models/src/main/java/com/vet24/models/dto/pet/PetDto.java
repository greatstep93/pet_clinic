package com.vet24.models.dto.pet;

import com.vet24.models.enums.PetType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PetDto {
    private Long id;
    private String name;

    private String avatar;
    private LocalDate birthDay;

    private Integer notificationCount;

    private PetType petType; //dog, cat (в будущем добавим еще видов питомцев)
}
