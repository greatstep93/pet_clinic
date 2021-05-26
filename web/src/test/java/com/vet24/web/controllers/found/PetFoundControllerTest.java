package com.vet24.web.controllers.found;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.vet24.models.dto.pet.PetContactDto;
import com.vet24.models.dto.pet.PetFoundDto;
import com.vet24.models.pet.PetContact;
import com.vet24.models.pet.PetFound;
import com.vet24.service.pet.PetContactService;
import com.vet24.web.ControllerAbstractIntegrationTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

@DBRider
public class PetFoundControllerTest extends ControllerAbstractIntegrationTest {

    @Autowired
    PetContactService petContactService;

    final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    // get save data found pet and create with send owner message about pet - success
    @Test
    @DataSet(cleanBefore = true, value = {"/datasets/pet-found.yml", "/datasets/pet-contact.yml", "/datasets/user-entities.yml", "/datasets/pet-entities.yml"})
    public void testSaveDataFoundPetAndSendOwnerPetMessage() throws Exception {
        PetContact petContact = petContactService.getByKey(104L);
        String petCode = petContact.getPetCode();
        final String URL_GET_PET_CONTACT_BY_PETCODE = "/api/petFound";
        PetFoundDto petFoundDto = new PetFoundDto("1.2345678", "2.3456789", "Some text");
        String bodyUpdate = (new ObjectMapper()).valueToTree(petFoundDto).toString();
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL_GET_PET_CONTACT_BY_PETCODE)
                .content(bodyUpdate).contentType(APPLICATION_JSON_UTF8)
                .param("petCode", petCode))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    // get PetContact by petCode is not found - error 404
    @Test
    @DataSet(cleanBefore = true, value = {"/datasets/pet-found.yml", "/datasets/pet-contact.yml", "/datasets/user-entities.yml", "/datasets/pet-entities.yml"})
    public void testSaveDataFoundPetAndSendOwnerPetMessageError404Pet() throws Exception {
        String petCode = "CD0964F7A769B65E2BA57822840B0E53";
        final String URL_GET_PET_CONTACT_BY_PETCODE = "/api/petFound";
        PetFoundDto petFoundDto = new PetFoundDto("1.2345678", "2.3456789", "Some text");
        String bodyUpdate = (new ObjectMapper()).valueToTree(petFoundDto).toString();
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL_GET_PET_CONTACT_BY_PETCODE)
                .content(bodyUpdate).contentType(APPLICATION_JSON_UTF8)
                .param("petCode", petCode))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}
