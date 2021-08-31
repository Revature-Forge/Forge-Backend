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
import com.forge.revature.controllers.AboutMeController;
import com.forge.revature.models.AboutMe;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.repo.AboutMeRepo;
import com.forge.revature.services.AboutMeService;

/**
 * @author Max Lee
 * @version 1.0
 * 
 * Tests for the AboutMe MVC process.
 */
@SpringBootTest
public class AboutMeTests {
    private MockMvc mockMvc;
    private AboutMe testAboutMe;
    private static String baseUrl = "/api/aboutMe";
    
    
    @MockBean
    AboutMeRepo aboutMeRepo;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
    	HashMap<String, String> map = new HashMap<>();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AboutMeController(new AboutMeService(aboutMeRepo))).build();
        User user = new User(1, "Max", "Lee" , "max.lee@email.com" , "password", true);
        Portfolio portfolio = new Portfolio(1, "My Portfolio", user, false, false, false, "", map);
        this.testAboutMe = new AboutMe(1, portfolio, "Hi I'm Max", "max@mail.net", "(333) 333-4444");
    }

    @Test
    void testGetAll() throws Exception {
        given(this.aboutMeRepo.findAll()).willReturn(new ArrayList<AboutMe>());

        this.mockMvc.perform(get(baseUrl))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testPost() throws Exception {
        given(this.aboutMeRepo.findById(1)).willReturn(Optional.of(testAboutMe));
        AboutMe newAboutMe = new AboutMe("Hi I'm Max", "max@mail.net", "(333) 333-4444");

        this.mockMvc.perform(post(baseUrl)
            .contentType("application/json;charset=utf-8")
            .content(asJsonString(newAboutMe)))
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
        given(this.aboutMeRepo.findById(1)).willReturn(Optional.of(testAboutMe));

        this.mockMvc.perform(get(baseUrl + "/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testPostById() throws Exception {
        given(this.aboutMeRepo.findById(1)).willReturn(Optional.of(testAboutMe));

        AboutMe newAboutMe = new AboutMe("Hi I'm not Max", "maxUpdated@mail.net", "(222) 333-4444");

        this.mockMvc.perform(post(baseUrl + "/1")
            .contentType("application/json;charset=utf-8")
            .content(asJsonString(newAboutMe)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();

        this.mockMvc.perform(get("/api/aboutMe/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testDelete() throws Exception {
        given(this.aboutMeRepo.findById(1)).willReturn(Optional.of(testAboutMe));

        this.mockMvc.perform(delete(baseUrl + "/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();

        this.mockMvc.perform(get("/api/aboutMe/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetByUserId() throws Exception {
        given(this.aboutMeRepo.findByPortfolioUserId(1)).willReturn(Optional.of(testAboutMe));

        this.mockMvc.perform(get(baseUrl + "/user/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetByPortfolioId() throws Exception {
        given(this.aboutMeRepo.findByPortfolioId(1)).willReturn(Optional.of(testAboutMe));

        this.mockMvc.perform(get(baseUrl + "/portfolio/1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }
}
