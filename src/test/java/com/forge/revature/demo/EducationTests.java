package com.forge.revature.demo;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.EducationController;
import com.forge.revature.models.Education;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.repo.EducationRepo;

/**
 * @author Max Lee
 * @version 1.0
 * 
 * Tests for the Education MVC process.
 */
@SpringBootTest
public class EducationTests {
    private MockMvc mockMvc;
    private Education testEducation;
    private Education testEducation2;
    private Education testEducation3;
    private static String baseUrl = "/api/education";
    
    @MockBean
    EducationRepo educationRepo;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new EducationController(educationRepo)).build();
        User user = new User(1, "Max", "Lee" , "max.lee@email.com" , "password", true);
        HashMap<String, String> map = new HashMap<>();
        Portfolio portfolio = new Portfolio(1, "My Portfolio", user, false, false, false, "", null, map);
        this.testEducation = new Education(1, portfolio, "university", "degree", "graduationDate", 3.5, "");
        this.testEducation2 = new Education(2, portfolio, "uni", "deg", "2021", 2.0, "");
        this.testEducation3 = new Education(3, portfolio, "uni2", "deg2", "2021", 2.0, "");
    }

    @Test
    void testGetAll() throws Exception {
        given(this.educationRepo.findAll()).willReturn(new ArrayList<Education>());

        this.mockMvc.perform(get(baseUrl))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testPost() throws Exception {
        given(this.educationRepo.findById(1)).willReturn(Optional.of(testEducation));
        Education newEducation = new Education("Green River", "AA", "2020", 2.4, "");

        this.mockMvc.perform(post(baseUrl)
            .contentType("application/json;charset=utf-8")
            .content(asJsonString(newEducation)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();

        this.mockMvc.perform(get(baseUrl))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetById() throws Exception {
        given(this.educationRepo.findById(1)).willReturn(Optional.of(testEducation));

        this.mockMvc.perform(get(baseUrl + "/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testPostById() throws Exception {
        given(this.educationRepo.findById(1)).willReturn(Optional.of(testEducation));

        Education newEducation = new Education("Not a university", "Bachelor's", "2018", 3.5, "");

        this.mockMvc.perform(post(baseUrl + "/1")
            .contentType("application/json;charset=utf-8")
            .content(asJsonString(newEducation)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    void testDelete() throws Exception {
        given(this.educationRepo.findById(1)).willReturn(Optional.of(testEducation));

        this.mockMvc.perform(delete(baseUrl + "/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();

        this.mockMvc.perform(get(baseUrl + "/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetByUserId() throws Exception {
        given(this.educationRepo.findByPortfolioUserId(1)).willReturn(Optional.of(testEducation));

        this.mockMvc.perform(get(baseUrl + "/user/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetByPortfolioId() throws Exception {
        given(this.educationRepo.findByPortfolioId(1)).willReturn(Optional.of(testEducation));

        this.mockMvc.perform(get(baseUrl + "/portfolio/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetByUserIdAll() throws Exception {
        ArrayList<Education> list = new ArrayList<>();
        list.add(testEducation);
        list.add(testEducation2);
        list.add(testEducation3);
        given(this.educationRepo.findAllByPortfolioUserId(1)).willReturn(list);

        this.mockMvc.perform(get(baseUrl + "/user/all/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetByPortfolioIdAll() throws Exception {
        ArrayList<Education> list = new ArrayList<>();
        list.add(testEducation);
        list.add(testEducation2);
        list.add(testEducation3);
        given(this.educationRepo.findAllByPortfolioId(1)).willReturn(list);

        this.mockMvc.perform(get(baseUrl + "/portfolio/all/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }
}
