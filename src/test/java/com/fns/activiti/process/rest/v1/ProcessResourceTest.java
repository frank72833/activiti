package com.fns.activiti.process.rest.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fns.activiti.process.rest.v1.request.ProcessRequest;
import com.fns.activiti.process.rest.v1.response.ProcessResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class ProcessResourceTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    ProcessResourceTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void givenProcess_whenStartProcess_thenIncreaseInProcessInstanceCount() throws Exception {
        ProcessRequest req = ProcessRequest.builder().name("name").build();
        String responseBody = this.mockMvc.perform(post("/api/v1/processes/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProcessResponse result = objectMapper.readValue(responseBody, ProcessResponse.class);
        assertAll(
                () -> assertNotNull(result)
        );
    }

}