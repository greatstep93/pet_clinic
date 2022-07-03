package com.vet24.web.controllers.pet.procedure;

import com.vet24.models.dto.OnCreate;
import com.vet24.models.dto.OnUpdate;
import com.vet24.models.dto.exception.ExceptionDto;
import com.vet24.models.dto.pet.procedure.EchinococcusDto;
import com.vet24.models.enums.ProcedureType;
import com.vet24.models.exception.BadRequestException;
import com.vet24.models.mappers.pet.procedure.AbstractNewProcedureMapper;
import com.vet24.models.mappers.pet.procedure.EchinococcusMapper;
import com.vet24.models.medicine.Medicine;
import com.vet24.models.pet.Pet;
import com.vet24.models.pet.procedure.EchinococcusProcedure;
import com.vet24.models.user.Client;
import com.vet24.service.medicine.MedicineService;
import com.vet24.service.pet.PetService;
import com.vet24.service.pet.procedure.EchinococcusProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import static com.vet24.models.secutity.SecurityUtil.getSecurityUserOrNull;

@RestController
@Slf4j
@RequestMapping("api/client/procedure/deworming")
@Tag(name = "deworming-controller", description = "operations with Procedures")
public class DewormingController {

    private final PetService petService;
    private final EchinococcusProcedureService echinococcusProcedureService;
    private final EchinococcusMapper echinococcusMapper;
    private final AbstractNewProcedureMapper newProcedureMapper;
    private final MedicineService medicineService;

    private static final String PET_NOT_FOUND = "pet not found";
    private static final String PROCEDURE_NOT_FOUND = "deworming procedure not found";
    private static final String PET_NOT_YOURS = "pet not yours";
    private static final String PROCEDURE_NOT_YOURS = "procedure not yours";

    @Autowired
    public DewormingController(PetService petService, EchinococcusProcedureService echinococcusProcedureService,
                               AbstractNewProcedureMapper newProcedureMapper, EchinococcusMapper echinococcusMapper,
                               MedicineService medicineService) {
        this.petService = petService;
        this.echinococcusProcedureService = echinococcusProcedureService;

        this.echinococcusMapper = echinococcusMapper;
        this.newProcedureMapper = newProcedureMapper;
        this.medicineService = medicineService;
    }

    @Operation(summary = "get a deworming procedure by pet id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get a deworming procedure",
                    content = @Content(schema = @Schema(implementation = EchinococcusDto.class))),
            @ApiResponse(responseCode = "404", description = "Pet or deworming procedure not found",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "Pet not yours",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @GetMapping("")
    public ResponseEntity<EchinococcusDto> getByPetId(@RequestParam Long petId) {
        Client client = (Client) getSecurityUserOrNull();
        Pet pet = petService.getByKey(petId);

        if (pet == null) {
            log.info("The pet with this id {} was not found", petId);
            throw new NotFoundException(PET_NOT_FOUND);
        }
        if (!pet.getClient().getId().equals(client.getId())) {
            log.info("The pet with this id {} is not yours", petId);
            throw new BadRequestException(PET_NOT_YOURS);
        }

        EchinococcusProcedure echinococcusProcedure = (EchinococcusProcedure) pet.getProcedures().stream()
                .filter(p -> p.type == ProcedureType.ECHINOCOCCUS)
                .findFirst()
                .orElse(null);

        if (echinococcusProcedure == null) {
            log.info("The deworming procedure with this pet id {} was not found", petId);
            throw new NotFoundException(PROCEDURE_NOT_FOUND);
        }
        EchinococcusDto echinococcusDto = echinococcusMapper.toDto(echinococcusProcedure);
        log.info("We have this deworming procedure {}", echinococcusProcedure.getId());
        return new ResponseEntity<>(echinococcusDto, HttpStatus.OK);
    }

    @Operation(summary = "get a deworming procedure by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get a deworming procedure",
                    content = @Content(schema = @Schema(implementation = EchinococcusDto.class))),
            @ApiResponse(responseCode = "404", description = "Deworming procedure not found",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "Procedure not yours",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @GetMapping("/{procedureId}")
    public ResponseEntity<EchinococcusDto> getById(@PathVariable Long procedureId) {
        Client client = (Client) getSecurityUserOrNull();
        EchinococcusProcedure echinococcusProcedure = echinococcusProcedureService.getByKey(procedureId);
        if (echinococcusProcedure == null) {
            log.info("The deworming procedure with this id {} was not found", procedureId);
            throw new NotFoundException(PROCEDURE_NOT_FOUND);
        }

        Pet pet = echinococcusProcedure.getPet();

        if (!pet.getClient().getId().equals(client.getId())) {
            log.info("The deworming procedure with this id {} is not yours", procedureId);
            throw new BadRequestException(PROCEDURE_NOT_YOURS);
        }

        EchinococcusDto echinococcusDto = echinococcusMapper.toDto(echinococcusProcedure);
        log.info("We have this deworming procedure {}", procedureId);
        return new ResponseEntity<>(echinococcusDto, HttpStatus.OK);
    }

    @Operation(summary = "add a new deworming procedure")

    @PostMapping("")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added a new deworming procedure",
                    content = @Content(schema = @Schema(implementation = EchinococcusDto.class))),
            @ApiResponse(responseCode = "404", description = "Pet not found",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "Pet not yours",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    public ResponseEntity<EchinococcusDto> save(@RequestParam Long petId, @Validated(OnCreate.class)
    @RequestBody EchinococcusDto echinococcusDto) {

        Client client = (Client) getSecurityUserOrNull();
        Pet pet = petService.getByKey(petId);
        EchinococcusProcedure echinococcusProcedure = (EchinococcusProcedure) newProcedureMapper.toEntity(echinococcusDto);

        if (pet == null) {
            throw new NotFoundException(PET_NOT_FOUND);
        }
        if (!pet.getClient().getId().equals(client.getId())) {
            throw new BadRequestException(PET_NOT_YOURS);
        }

        Medicine medicine = medicineService.getByKey(echinococcusDto.getMedicineId());
        echinococcusProcedure.setMedicine(medicine);
        echinococcusProcedure.setPet(pet);
        echinococcusProcedureService.persist(echinococcusProcedure);


        pet.addProcedure(echinococcusProcedure);
        petService.update(pet);
        log.info("We added deworming procedure with this id {}", echinococcusProcedure.getId());
        return new ResponseEntity<>(echinococcusMapper.toDto(echinococcusProcedure), HttpStatus.CREATED);
    }

    @Operation(summary = "update a deworming procedure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a deworming procedure",
                    content = @Content(schema = @Schema(implementation = EchinococcusDto.class))),
            @ApiResponse(responseCode = "404", description = "Deworming procedure not found",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "Procedure not yours",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PutMapping("/{procedureId}")
    public ResponseEntity<EchinococcusDto> update(@PathVariable Long procedureId,
                                                  @Validated(OnUpdate.class) @RequestBody EchinococcusDto echinococcusDto) {

        Client client = (Client) getSecurityUserOrNull();
        EchinococcusProcedure echinococcusProcedure = echinococcusProcedureService.getByKey(procedureId);

        if (echinococcusProcedure == null) {
            throw new NotFoundException(PROCEDURE_NOT_FOUND);
        }

        Pet pet = echinococcusProcedure.getPet();

        if (!pet.getClient().getId().equals(client.getId())) {
            throw new BadRequestException(PROCEDURE_NOT_YOURS);
        }

        echinococcusMapper.updateEntity(echinococcusDto, echinococcusProcedure);
        Medicine medicine = medicineService.getByKey(echinococcusDto.getMedicineId());
        echinococcusProcedure.setMedicine(medicine);
        echinococcusProcedureService.update(echinococcusProcedure);
        log.info("We updated deworming procedure with this id {}", echinococcusProcedure.getId());

        return new ResponseEntity<>(echinococcusMapper.toDto(echinococcusProcedure), HttpStatus.OK);
    }

    @Operation(summary = "delete a deworming procedure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a deworming procedure"),
            @ApiResponse(responseCode = "404", description = "Deworming procedure not found",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "Procedure not yours",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @DeleteMapping("/{procedureId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long procedureId) {

        Client client = (Client) getSecurityUserOrNull();
        EchinococcusProcedure echinococcusProcedure = echinococcusProcedureService.getByKey(procedureId);

        if (echinococcusProcedure == null) {
            throw new NotFoundException(PROCEDURE_NOT_FOUND);
        }

        Pet pet = echinococcusProcedure.getPet();

        if (!pet.getClient().getId().equals(client.getId())) {
            throw new BadRequestException(PROCEDURE_NOT_YOURS);
        }

        echinococcusProcedureService.delete(echinococcusProcedure);
        pet.removeProcedure(echinococcusProcedure);
        petService.update(pet);
        log.info("We deleted deworming procedure with this id {}", echinococcusProcedure.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
