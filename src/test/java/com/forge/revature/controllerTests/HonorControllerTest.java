package com.forge.revature.controllerTests;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.HonorController;
import com.forge.revature.models.Honor;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.repo.HonorRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.services.HonorService;

@SpringBootTest
public class HonorControllerTest {

  MockMvc mvc;
  
  static String baseUrl = "/api/honor";

  HonorController honorController;

  HonorService honorService;
  
  @MockBean
  HonorRepo honorRepo;

  @MockBean
  PortfolioRepo portfolioRepo;

  Honor honor;

  @BeforeEach
  public void setup() {
	this.honorService = new HonorService(honorRepo, portfolioRepo);
	this.honorController = new HonorController(honorService);
	this.mvc = MockMvcBuilders.standaloneSetup(honorController).build();
    this.honor = new Honor("Developer of the Year", "Top Performing Developer", "2019", "Revature");
    this.honor.setId(1);
  }

  @Test
  public void testGetAll() throws Exception {
    List<Honor> allHonors = Arrays.asList(honor);
  
    given(honorRepo.findAll()).willReturn(allHonors);

    mvc.perform(get(baseUrl)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].title", is(honor.getTitle())));
  }

  @Test
  public void testGet() throws Exception {
    given(honorRepo.findById(1)).willReturn(Optional.of(honor));

    mvc.perform(get(baseUrl + "/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title", is(honor.getTitle()))); //making sure getting the right data
  }

  @Test
  public void testPost() throws Exception {
    given(honorRepo.save(Mockito.any())).willReturn(honor);

    mvc.perform(post(baseUrl)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(honor)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title", is(honor.getTitle())));
  }

  @Test
  void testDelete() throws Exception {
    given(honorRepo.findById(1)).willReturn(Optional.of(honor));
    mvc.perform(delete(baseUrl + "/1"))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  void testUpdate() throws Exception {
    given(honorRepo.findById(1)).willReturn(Optional.of(honor));

    Honor newHonor = new Honor("Updated title", "updated description", "Updated dateReceived", "Updated receivedFrom");
    newHonor.setId(1);

    given(honorRepo.save(Mockito.any())).willReturn(newHonor);
   
    mvc.perform(put(baseUrl)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newHonor)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title", is(newHonor.getTitle())));
  }

  @Test
  void testGetByPortfolioId() throws Exception {
	  HashMap<String, String> map = new HashMap<>();
    Portfolio portfolio = new Portfolio(1, "new portfolio", new User(1, "test" , "user", "test@email.com" , "password", false), false, false, false, "", map);
    honor.setPortfolio(portfolio);
    List<Honor> allHonors = Arrays.asList(honor);
  
    given(honorRepo.findByPortfolio(portfolio)).willReturn(allHonors);
    given(portfolioRepo.findById(1)).willReturn(Optional.of(portfolio));

    mvc.perform(get(baseUrl + "/portfolio/1"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].title", is(honor.getTitle())))
      .andExpect(jsonPath("$[0].portfolio.id", is(portfolio.getId())));
}