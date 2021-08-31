package com.forge.revature.controllerTests;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.models.Certification;
import com.forge.revature.repo.CertificationRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
public class CertificationControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
  
    private MockMvc mockMvc;

    private static String baseUrl = "/api/certifications";
    
    @MockBean
    CertificationRepo certificationRepo;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(baseUrl))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"));
    }

    @Test
    void testGetById() throws Exception {
        Date dateForTest = new Date();
        Certification certForTest = new Certification("Test", "123456", "Tester", dateForTest, "testurl");
        Optional<Certification> cert = Optional.of(certForTest);

        given(certificationRepo.findById(1L)).willReturn(cert);

        mockMvc.perform(get(baseUrl + "/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"));
    }

    @Test
    void testGetAllByPortfolioId() throws Exception {
        given(this.certificationRepo.findAllByPortfolioId(1)).willReturn(new ArrayList<Certification>());

        mockMvc.perform(get(baseUrl + "/portfolio/all/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    void testPostCertification() throws Exception {
        Date dateForTest = new Date();
        Certification certForTest = new Certification("Test", "123456", "Tester", dateForTest, "testurl");

        ObjectMapper objectMapper = new ObjectMapper();
        String cert = objectMapper.writeValueAsString(certForTest);

        mockMvc.perform(
            post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cert))
            .andExpect(status().isOk());
    }

    @Test
    void testUpdateCertification() throws Exception {
        Date dateForTest = new Date();
        Certification certForTest = new Certification("Test", "123456", "Tester", dateForTest, "testurl");
        Certification newcertForTest = new Certification("NewTest", "654321", "NewTester", dateForTest, "newtesturl");
        Optional<Certification> cert = Optional.of(certForTest);
        ObjectMapper objectMapper = new ObjectMapper();
        String certForUpdate = objectMapper.writeValueAsString(newcertForTest);
        
        given(certificationRepo.findById(1L)).willReturn(cert);

        mockMvc.perform(
            post(baseUrl + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(certForUpdate))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
    }

    @Test
    void testDeleteCertification() throws Exception {
        Date dateForTest = new Date();
        Certification certForTest = new Certification("Test", "123456", "Tester", dateForTest, "testurl");
        Optional<Certification> cert = Optional.of(certForTest);

        given(certificationRepo.findById(1L)).willReturn(cert);

        mockMvc.perform(delete(baseUrl + "/1"))
            .andExpect(status().isOk());
    }
}
