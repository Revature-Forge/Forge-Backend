package com.forge.revature.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.UserController;
import com.forge.revature.models.User;
import com.forge.revature.repo.UserRepo;
import com.forge.revature.services.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class UserTest {
    
    private MockMvc mvc;

    private static String baseUrl = "/api/users";
    
    @MockBean
    UserRepo repo;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .standaloneSetup(new UserController(new UserService(repo)))
            .build();
    }

    @Test
    void testGetall() throws Exception {
        System.out.println(repo);
        given(repo.findAll())
            .willReturn(new ArrayList<User>());

        mvc.perform(get(baseUrl))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }
    @Test
    void testGetbyEmail() throws Exception {
        User user = new User(1, "John", "Doe", "test@test.com", "password", false);
        given(repo.findByEmail("test@test.com")).willReturn(Optional.of(user));

        mvc.perform(post(baseUrl + "/login")
            .header("email" ,"test@test.com")
            .header("password", "password"))
            
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }
    @Test
    void testGetbyWrongEmail() throws Exception {
        User user = new User(1, "John", "Doe", "test@test.com", "password", false);
        given(repo.findByEmail("test@test.com")).willReturn(Optional.of(user));

        mvc.perform(post(baseUrl + "/login")
            .header("email" ,"test1@test.com")
            .header("password", "password"))
            
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isUnauthorized())
            .andReturn();
    }
    @Test
    void testGetbyEmailWithWrongPassword() throws Exception {
        User user = new User(1, "John", "Doe", "test@test.com", "password", false);
        given(repo.findByEmail("test@test.com")).willReturn(Optional.of(user));

        mvc.perform(post(baseUrl + "/login")
        .header("email" ,"test@test.com")
        .header("password", "password1"))
            
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isUnauthorized())
            .andReturn();
    }

    @Test
    void testGetById() throws Exception{
        given(repo.findById(1)).willReturn(Optional.of(new User()));


        mvc.perform(get(baseUrl + "/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testPost() throws Exception{
        User user = new User(1, "John", "Doe", "test@test.com", "password", false);
            
        
        given(repo.save(user)).willReturn(user);

        mvc.perform(post(baseUrl)
            .contentType("application/json;charset=utf-8")
            .content(new ObjectMapper().writeValueAsString(user)))

            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }

    void testUpdate() throws Exception{
        User user = new User(1, "John", "Smith", "test@test.com", "password", false);
        User user2 = new User(1, "Joe", "Johnson", "newtest@test.com" , "password new", true);
        Optional<User> returned = Optional.of(user);
        

        given(repo.findById(1)).willReturn(returned);

        mvc.perform(post(baseUrl + "/1")
            .contentType("application/json")
            .content(new ObjectMapper().writeValueAsString(user2)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
    }
    @Test
    void testdelete() throws Exception {
        User user = new User(1, "Jeffery", "Lebowski", "abide@thedude.com", "AB1D3", false);
        Optional<User> returned = Optional.of(user);

        given(repo.findById(1)).willReturn(returned);

        mvc.perform(delete(baseUrl + "/1"))
            .andExpect(status().isOk());
    }

}
