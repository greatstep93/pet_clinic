package com.vet24.web.controllers.user;


import com.vet24.models.dto.user.DoctorNonWorkingDto;
import com.vet24.models.mappers.user.DoctorNonWorikingMapper;
import com.vet24.models.user.DoctorNonWorking;
import com.vet24.service.user.DoctorNonWorkingService;
import com.vet24.service.user.DoctorService;
import com.vet24.service.user.UserService;
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


@RestController
@RequestMapping(value = "api/admin/doctor_non_working/")
@Tag(name = "admin doctor_non_working controller", description = "operations with doctor_non_working")
@Slf4j
public class AdminDoctorNonWorkingController {

    private final DoctorNonWorkingService doctorNonWorkingService;
    private final DoctorNonWorikingMapper doctorNonWorikingMapper;
    private final DoctorService doctorService;

    @Autowired
    public AdminDoctorNonWorkingController(DoctorNonWorkingService doctorNonWorkingService, DoctorNonWorikingMapper doctorNonWorikingMapper, DoctorService doctorService) {
        this.doctorNonWorkingService = doctorNonWorkingService;
        this.doctorNonWorikingMapper = doctorNonWorikingMapper;
        this.doctorService = doctorService;
    }


    @Operation(summary = "create doctorNonWorking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created new DoctorNonWorking"),
            @ApiResponse(responseCode = "404", description = "Doctor in DoctorNonWorking not found")
    })
    @PostMapping("")
    public ResponseEntity<DoctorNonWorkingDto> createDoctorNonWorking(@RequestBody DoctorNonWorkingDto doctorNonWorkingDto){
        DoctorNonWorking doctorNonWorking = doctorNonWorikingMapper.toEntity(doctorNonWorkingDto);
        Long docId = doctorNonWorkingDto.getDoctorId();
        if(doctorService.getByKey(docId)!=null){
            doctorNonWorking.setDoctor(doctorService.getByKey(docId));
            doctorNonWorkingService.persist(doctorNonWorking);
            log.info("DoctorNonWorking create");
            return ResponseEntity.ok(doctorNonWorkingDto);
        }else {
            log.info("DoctorNonWorking have bad doctorId");
            throw new NotFoundException("Doctor not found");
        }

    }



    @Operation(summary = "edit doctorNonWorking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DoctorNonWorking updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorNonWorkingDto.class))),
            @ApiResponse(responseCode = "404", description = "DoctorNonWorking not found")
    })
    @PutMapping("{id}")
    public ResponseEntity<DoctorNonWorkingDto> editDoctorNonWorking(@RequestBody DoctorNonWorkingDto doctorNonWorkingDto,
                                                                   @PathVariable("id") Long id){
        if(doctorNonWorkingService.isExistByKey(id)){
            log.info("DoctorNonWorking with id {}",id);
            Long docId = doctorNonWorkingDto.getDoctorId();
            if(doctorService.getByKey(docId)!=null){
                DoctorNonWorking doctorNonWorking = doctorNonWorikingMapper.toEntity(doctorNonWorkingDto);
                doctorNonWorking.setId(id);
                doctorNonWorking.setDoctor(doctorService.getByKey(docId));
                doctorNonWorkingService.update(doctorNonWorking);
                return ResponseEntity.ok(doctorNonWorkingDto);
            }else {
                log.info("DoctorNonWorking have bad doctorId");
                throw new NotFoundException("Doctor not found");
            }
        }else {
            log.info("DoctorNonWorking with id {} not found", id);
            throw new NotFoundException("DoctorNonWorking not found");
        }
    }



    @Operation(summary = "delete doctorNonWorking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DoctorNonWorking deleted"),
            @ApiResponse(responseCode = "404", description = "DoctorNonWorking not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDoctorNonWorking(@PathVariable("id") Long id){
        if(doctorNonWorkingService.isExistByKey(id)){
            doctorNonWorkingService.delete(doctorNonWorkingService.getByKey(id));
            log.info("DoctorNonWorking with id {} deleted", id);
        }else {
            log.info("DoctorNonWorking with id {} not found", id);
            throw new NotFoundException("DoctorNonWorking not found");
        }
        return ResponseEntity.ok().build();

    }




}
