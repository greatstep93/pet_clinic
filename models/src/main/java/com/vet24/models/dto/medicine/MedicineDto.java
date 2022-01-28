package com.vet24.models.dto.medicine;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.vet24.models.util.View;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDto {
    @JsonView({View.Get.class})
    Long id;
    @NotBlank( message = "поле manufactureName не должно быть пустым")
    @JsonView({View.Put.class, View.Get.class})
    String manufactureName;

    @NotBlank( message = "поле name не должно быть пустым")
    @JsonView({View.Put.class, View.Get.class})
    String name;
    @JsonView({View.Put.class, View.Get.class})
    String icon;
    @NotBlank( message = "поле description не должно быть пустым")
    @JsonView({View.Put.class, View.Get.class})
    String description;
}
