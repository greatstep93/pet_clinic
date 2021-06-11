package com.vet24.web.controllers.medicine;

import com.vet24.models.dto.medicine.DiagnosisDto;
import com.vet24.models.dto.medicine.TreatmentDto;
import com.vet24.models.dto.pet.procedure.AbstractNewProcedureDto;
import com.vet24.models.mappers.medicine.DiagnosisMapper;
import com.vet24.models.mappers.medicine.TreatmentMapper;
import com.vet24.models.mappers.pet.procedure.ProcedureMapper;
import com.vet24.models.medicine.Diagnosis;
import com.vet24.models.medicine.Treatment;
import com.vet24.models.pet.Pet;
import com.vet24.models.pet.procedure.Procedure;
import com.vet24.models.user.Doctor;
import com.vet24.service.medicine.DiagnosisService;
import com.vet24.service.medicine.MedicineService;
import com.vet24.service.medicine.TreatmentService;
import com.vet24.service.pet.PetService;
import com.vet24.service.pet.procedure.ProcedureService;
import com.vet24.service.user.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctor")
@Tag(name = "doctor-controller", description = "Doctor's operations")
public class DoctorController {
    private final PetService petService;
    private final DoctorService doctorService;
    private final DiagnosisService diagnosisService;
    private final DiagnosisMapper diagnosisMapper;
    private final TreatmentService treatmentService;
    private final TreatmentMapper treatmentMapper;
    private final ProcedureMapper procedureMapper;
    private final ProcedureService procedureService;
    private final MedicineService medicineService;


    public DoctorController(PetService petService, DoctorService doctorService,
                            DiagnosisService diagnosisService, DiagnosisMapper diagnosisMapper, TreatmentService treatmentService, TreatmentMapper treatmentMapper, ProcedureMapper procedureMapper, ProcedureService procedureService, MedicineService medicineService, MedicineService medicineService1) {
        this.petService = petService;
        this.doctorService = doctorService;
        this.diagnosisService = diagnosisService;
        this.diagnosisMapper = diagnosisMapper;
        this.treatmentService = treatmentService;
        this.treatmentMapper = treatmentMapper;
        this.procedureMapper = procedureMapper;
        this.procedureService = procedureService;
        this.medicineService = medicineService1;
    }

    @Operation(summary = "add a new diagnosis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added new diagnosis",
                    content = @Content(schema = @Schema(implementation = DiagnosisDto.class))),
            @ApiResponse(responseCode = "404", description = "Pet is not found")
    })

    @PostMapping("/pet/{petId}/addDiagnosis")
    public ResponseEntity<DiagnosisDto> addDiagnosis(@PathVariable Long petId,
                                                     @RequestBody String text){
        Doctor doctor = doctorService.getCurrentDoctor();
        Pet pet = petService.getByKey(petId);
        if(pet == null){
            throw new NotFoundException("No such pet found");
        }
        Diagnosis diagnosis = new Diagnosis(doctor,pet,text);
        diagnosisService.persist(diagnosis);
        return new ResponseEntity<>(diagnosisMapper.diagnosisToDiagnosisDto(diagnosis),
                HttpStatus.CREATED);
    }

    @Operation(summary = "add treatment for diagnose")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added treatment ",
            content = @Content(schema = @Schema(implementation = TreatmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Unknown medicine")
    })

    @PostMapping("/diagnosis/{diagnoseId}/addTreatment")
    public ResponseEntity<TreatmentDto> addTreatment(@PathVariable Long diagnoseId,
                                                     @RequestBody List<AbstractNewProcedureDto> procedures){
        Diagnosis diagnosis = diagnosisService.getByKey(diagnoseId);
        Treatment treatment = new Treatment();
        List<Procedure> procedureList = procedures.stream()
                .map(procedureMapper::abstractNewProcedureDtoToProcedure)
                .map(x ->{
                    if(!medicineService.isExistByKey(x.getMedicine().getId())){
                        throw new NotFoundException("unknown medicine with id: " + x.getMedicine().getId()
                                + " in the procedure with type: " + x.getType());
                    }
                    else {
                        return x;
                    }
                })
                .collect(Collectors.toList());
        procedureService.persistAll(procedureList);
        treatment.setProcedureList(procedureList);
        treatment.setDiagnosis(diagnosis);
        treatmentService.persist(treatment);
        TreatmentDto treatmentDto = treatmentMapper.treatmentToTreatmentDto(treatment);
        return new ResponseEntity<>(treatmentDto,HttpStatus.CREATED);
    }
}
