package com.vet24.web.controllers.user;

import com.vet24.models.dto.user.DoctorNonWorkingDto;
import com.vet24.models.exception.DoctorNonWorkingEventException;
import com.vet24.models.mappers.user.DoctorNonWorkingMapper;
import com.vet24.models.user.Doctor;
import com.vet24.models.user.DoctorNonWorking;
import com.vet24.service.user.DoctorNonWorkingService;
import com.vet24.service.user.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "api/admin/doctor_non_working/")
@Tag(name = "admin doctor_non_working controller", description = "operations with doctor_non_working")
@Slf4j
public class AdminDoctorNonWorkingController {

    private final DoctorNonWorkingService doctorNonWorkingService;
    private final DoctorNonWorkingMapper doctorNonWorkingMapper;
    private final DoctorService doctorService;

    @Autowired
    public AdminDoctorNonWorkingController(DoctorNonWorkingService doctorNonWorkingService, DoctorNonWorkingMapper doctorNonWorkingMapper, DoctorService doctorService) {
        this.doctorNonWorkingService = doctorNonWorkingService;
        this.doctorNonWorkingMapper = doctorNonWorkingMapper;
        this.doctorService = doctorService;
    }

    @Operation(summary = "create doctorNonWorking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created new DoctorNonWorking"),
            @ApiResponse(responseCode = "404", description = "Doctor in DoctorNonWorking not found"),
            @ApiResponse(responseCode = "422", description = "Doctor already has an event on the day")
    })
    @PostMapping("")
    public ResponseEntity<DoctorNonWorkingDto> createDoctorNonWorking(@Valid @RequestBody DoctorNonWorkingDto doctorNonWorkingDto) {
        DoctorNonWorking doctorNonWorking = doctorNonWorkingMapper.toEntity(doctorNonWorkingDto);
        Doctor doc = doctorService.getByKey(doctorNonWorkingDto.getDoctorId());
        LocalDate date = doctorNonWorkingDto.getDate();

        if (doc == null) {
            log.info("DoctorNonWorking have bad doctorId");
            throw new NotFoundException("Doctor not found");
        }

        boolean exist = doctorNonWorkingService.isExistByDoctorIdAndDate(doc, date);
        if (exist) {
            log.info("This doctor already has an event scheduled for this date");
            throw new DoctorNonWorkingEventException("Doctor already has an event on the day");
        }

        doctorNonWorking.setDoctor(doc);
        doctorNonWorkingService.persist(doctorNonWorking);
        log.info("DoctorNonWorking create");
        Long idDnw = doctorNonWorking.getId();
        doctorNonWorkingDto.setDoctorNonWorkingId(idDnw);
        return ResponseEntity.ok(doctorNonWorkingDto);
    }

    @Operation(summary = "edit doctorNonWorking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DoctorNonWorking updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorNonWorkingDto.class))),
            @ApiResponse(responseCode = "404", description = "DoctorNonWorking not found"),
            @ApiResponse(responseCode = "422", description = "Doctor already has an event on the day")
    })
    @PutMapping("{id}")
    public ResponseEntity<DoctorNonWorkingDto> editDoctorNonWorking(@Valid @RequestBody DoctorNonWorkingDto doctorNonWorkingDto,
                                                                    @PathVariable("id") Long id) {
        if (doctorNonWorkingService.isExistByKey(id)) {
            log.info("DoctorNonWorking with id {}", id);
        } else {
            log.info("DoctorNonWorking with id {} not found", id);
            throw new NotFoundException("DoctorNonWorking not found");
        }

        DoctorNonWorking doctorNonWorking = doctorNonWorkingMapper.toEntity(doctorNonWorkingDto);
        Doctor doc = doctorService.getByKey(doctorNonWorkingDto.getDoctorId());
        LocalDate date = doctorNonWorkingDto.getDate();

        if (doc == null) {
            log.info("DoctorNonWorking have bad doctorId");
            throw new NotFoundException("Doctor not found");
        }

        boolean exist = doctorNonWorkingService.isExistByDoctorIdAndDate(doc, date);
        if (exist) {
            log.info("This doctor already has an event scheduled for this date");
            throw new DoctorNonWorkingEventException("Doctor already has an event on the day");
        }

        doctorNonWorking.setId(id);
        doctorNonWorking.setDoctor(doc);
        doctorNonWorkingService.update(doctorNonWorking);
        Long idDnw = doctorNonWorking.getId();
        doctorNonWorkingDto.setDoctorNonWorkingId(idDnw);
        return ResponseEntity.ok(doctorNonWorkingDto);
    }

    @Operation(summary = "delete doctorNonWorking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DoctorNonWorking deleted"),
            @ApiResponse(responseCode = "404", description = "DoctorNonWorking not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDoctorNonWorking(@PathVariable("id") Long id) {
        if (doctorNonWorkingService.isExistByKey(id)) {
            doctorNonWorkingService.delete(doctorNonWorkingService.getByKey(id));
            log.info("DoctorNonWorking with id {} deleted", id);
        } else {
            log.info("DoctorNonWorking with id {} not found", id);
            throw new NotFoundException("DoctorNonWorking not found");
        }
        return ResponseEntity.ok().build();
    }
}
