package com.vet24.web.controllers.user;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.vet24.models.dto.medicine.DiagnosisDto;
import com.vet24.models.user.Doctor;
import com.vet24.service.user.DoctorService;
import com.vet24.web.ControllerAbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

@DBRider
public class DoctorControllerTest extends ControllerAbstractIntegrationTest {

    @Autowired
    DoctorService doctorService;

    public static final String ADD_DIAGNOSIS_API ="/api/doctor/pet/{petId}/addDiagnosis";
    final String DOMAIN = "http://localhost:8090";

    @Test
    @DataSet(value = {"/datasets/roles.yml","/datasets/clients.yml","/datasets/doctors.yml"}, cleanBefore = true)
    public void shouldBeNotFound()  {
       String diagnosis = "bla-bla-bla";

        Doctor doctor = doctorService.getCurrentDoctor();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(diagnosis, headers);
        ResponseEntity<DiagnosisDto> responseEntity =  testRestTemplate
                .exchange(DOMAIN+ADD_DIAGNOSIS_API, HttpMethod.POST,entity, DiagnosisDto.class,1001);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    @DataSet(value = {"/datasets/roles.yml","/datasets/clients.yml","/datasets/doctors.yml"}, cleanBefore = true)
    public void shouldBeCreated()  {
        String diagnosis = "bla-bla-bla";
        Doctor doctor = doctorService.getCurrentDoctor();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(diagnosis, headers);
        ResponseEntity<DiagnosisDto> responseEntity =  testRestTemplate
                .exchange(DOMAIN+ADD_DIAGNOSIS_API, HttpMethod.POST,entity, DiagnosisDto.class,101L);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertEquals("bla-bla-bla",responseEntity.getBody().getDescription());
        Assert.assertEquals(doctor.getId(),responseEntity.getBody().getDoctorId());
        Assert.assertEquals(Long.valueOf(101L),responseEntity.getBody().getPetId());
        Assert.assertNotNull(responseEntity.getBody().getId());
        Assert.assertNotNull(responseEntity.getBody().getDoctorId());
        Assert.assertEquals(responseEntity.getBody().getDoctorId(),doctor.getId());

    }

}