package com.vet24.models.dto.pet.procedure;

import com.vet24.models.dto.OnCreate;
import com.vet24.models.dto.OnUpdate;
import com.vet24.models.enums.ProcedureType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcedureDto {
    @Null(groups = {OnCreate.class})
    @NotNull(groups = {OnUpdate.class})
    Long id;

    LocalDate date; //if null or blank set now
    ProcedureType type;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    Long medicineId;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    String medicineBatchNumber;

    Boolean isPeriodical;
    Integer periodDays;
}
