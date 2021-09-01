package com.forge.revature.demo;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.ProjectController;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Project;
import com.forge.revature.repo.ProjectRepo;
import com.forge.revature.services.ProjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class ProjectTest {

    private MockMvc mock;
    
    private static String baseUrl = "/api/projects";

    @MockBean
    ProjectRepo repo;

    @BeforeEach
    public void setup() {
        mock = MockMvcBuilders.standaloneSetup(new ProjectController(new ProjectService(repo))).build();
    }
    
    @Test
    void testGetAll() throws Exception {
        given(repo.findAll()).willReturn(new ArrayList<Project>());

        mock.perform(get(baseUrl))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetById() throws Exception {
        given(repo.findById((long) 1)).willReturn(Optional.of(new Project("Project 3", "sample description",
                "sample responsibilities", "sample technologies", "sample repository")));

        mock.perform(get(baseUrl + "/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testUpdate() throws Exception {
        Project proj = new Project("Project 3", "sample description", "sample responsibilities", "sample technologies",
                "sample repository");
        given(repo.findById((long) 1)).willReturn(Optional.of(proj));

        Project newProj = new Project("Project 3", "different description", "different responsibilities", "different technologies",
                "new repository");

        mock.perform(post(baseUrl + "/1").contentType("application/json;charset=utf-8").content(new ObjectMapper().writeValueAsString(newProj)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    void testDelete() throws Exception {
        mock.perform(delete(baseUrl + "/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    void testGetByPortfolio() throws Exception {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1);
        Project proj = new Project("Project 3", "sample description", "sample responsibilities", "sample technologies",
                "sample repository", portfolio);
        Project proj2 = new Project("Project 2", "new description", "my responsibilities", "other technologies",
                "different repository", portfolio);

        ArrayList<Project> list = new ArrayList<>();
        list.add(proj);
        list.add(proj2);
        given(repo.findByPortfolio_Id(1)).willReturn(list);

        mock.perform(get(baseUrl + "/portfolio/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testCreate() throws Exception {
        Project proj = new Project(1, "Project 3", "sample description", "sample responsibilities", "sample technologies",
                "sample repository", "sample workproducts", null);
                
        mock.perform(post(baseUrl).contentType("application/json;charset=utf-8").content(new ObjectMapper().writeValueAsString(proj)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }
}
