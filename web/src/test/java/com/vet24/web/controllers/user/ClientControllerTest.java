package com.vet24.web.controllers.user;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.vet24.models.dto.user.ClientDto;
import com.vet24.web.ControllerAbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@WithUserDetails(value = "client1@email.com")
@DataSet(cleanBefore = true, value = {"/datasets/user-entities.yml", "/datasets/pet-entities.yml"})
public class ClientControllerTest extends ControllerAbstractIntegrationTest {

    private final String URI = "/api/client";

    private final Principal principal = () -> "client1@email.com";

    @Test
    public void shouldGetResponseEntityClientDto_ForCurrentClient() {
        ResponseEntity<ClientDto> response = testRestTemplate.getForEntity(URI, ClientDto.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(principal.getName(), response.getBody().getEmail());
    }

    @Test
    public void uploadClientAvatarAndVerify() throws Exception {
        /*
        // Resolved Exception: Type = org.springframework.web.multipart.support.MissingServletRequestPartException
        // Error message = Required request part 'file' is not present

        ClassPathResource classPathResource = new ClassPathResource("test.png");
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", classPathResource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<UploadedFileDto> response = testRestTemplate.postForEntity(URI + "/avatar", requestEntity, UploadedFileDto.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());*/

        ClassPathResource classPathResource = new ClassPathResource("test.png");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",
                classPathResource.getFilename(), null, classPathResource.getInputStream());
        mockMvc.perform(multipart(URI + "/avatar")
                .file(mockMultipartFile).header("Content-Type", "multipart/form-data"))
                .andExpect(status().isOk());

        ResponseEntity<byte[]> response = testRestTemplate.getForEntity(URI + "/avatar", byte[].class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}