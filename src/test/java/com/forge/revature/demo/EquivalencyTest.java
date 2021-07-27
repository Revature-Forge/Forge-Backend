package com.forge.revature.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.EquivalencyController;
import com.forge.revature.models.Equivalency;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.repo.EquivalencyRepo;

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

@SpringBootTest
public class EquivalencyTest {
    private MockMvc mvc;

    @MockBean
    EquivalencyRepo repo;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .standaloneSetup(new EquivalencyController(repo))
            .build();
    }

    @Test
    void testGetall() throws Exception {
        System.out.println(repo);
        given(repo.findAll())
            .willReturn(new ArrayList<Equivalency>());

        mvc.perform(get("/equiv"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetById() throws Exception{
        given(repo.findById(1)).willReturn(Optional.of(new Equivalency()));


        mvc.perform(get("/equiv/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetAllByPortfolioId() throws Exception{
        given(repo.findAllByPortfolioId(1)).willReturn(new ArrayList<Equivalency>());

        mvc.perform(get("/equiv/portfolios/all/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }


    @Test
    void testPost() throws Exception{
    	HashMap<String,String> map = new HashMap<>();
        Equivalency port = new Equivalency(1, "testheader", 3  ,new Portfolio(1, "new portfilio", new User(1, "test", "user" , "test@email.com" ,"password", false), false, false, false, "", map));
            
        
        given(repo.save(port)).willReturn(port);

        mvc.perform(post("/equiv")
            .contentType("application/json;charset=utf-8")
            .content(new ObjectMapper().writeValueAsString(port)))

            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }
    @Test
    void testUpdate() throws Exception{
    	HashMap<String,String> map = new HashMap<>();
        Equivalency equiv = new Equivalency(1, "equiv", 1 , new Portfolio(1, "new portfilio", new User(1, "test", "user" , "test@email.com" , "password", false), false, false, false, "", map));
        Equivalency equiv2 = new Equivalency(1, "equiv renamed", 3 , new Portfolio(1, "new portfilio", new User(1, "test" , "user", "email@test.com" , "password", false), false, false, false, "", map));
        Optional<Equivalency> returned = Optional.of(equiv);
        

        given(repo.findById(1)).willReturn(returned);

        mvc.perform(post("/equiv/1")
            .contentType("application/json")
            .content(new ObjectMapper().writeValueAsString(equiv2)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
    }

    @Test
    void testdelete() throws Exception {
    	HashMap<String,String> map = new HashMap<>();
        Equivalency equiv = new Equivalency(1, "equiv", 1 , new Portfolio(1, "new portfilio", new User(1, "test" , "user", "testemail@test.com" , "password", false), false, false, false, "", map));
        Optional<Equivalency> returned = Optional.of(equiv);

        given(repo.findById(1)).willReturn(returned);

        mvc.perform(delete("/equiv/1")).andExpect(status().isOk());
    }
}
