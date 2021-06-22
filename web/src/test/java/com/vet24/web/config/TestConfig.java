package com.vet24.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.*;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;

@Profile("TestProfile")
@Configuration
public class TestConfig {

    @Bean
    @Primary
    @Scope("prototype")
    public TestRestTemplate anotherRestTemplate(@Autowired MockMvc mockMvc, @Autowired RestTemplateBuilder restTemplateBuilder) {
        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        return new TestRestTemplate(restTemplateBuilder.requestFactory(() -> requestFactory));
    }
}
