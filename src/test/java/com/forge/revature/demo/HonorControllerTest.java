package com.forge.revature.demo;

import com.forge.revature.controllers.HonorController;
import com.forge.revature.repo.HonorRepo;
import com.forge.revature.models.Honor;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HonorController.class)
public class HonorControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private HonorRepo honorRepo;

  @MockBean
  private PortfolioRepo portfolioRepo;

  private Honor honor;

  @BeforeEach
  public void setup() {
    this.honor = new Honor("Developer of the Year", "Top Performing Developer", "2019", "Revature");
    this.honor.setId(1);
  }

  @Test
  public void testGetAll() throws Exception {
    List<Honor> allHonors = Arrays.asList(honor);
  
    given(honorRepo.findAll()).willReturn(allHonors);

    mvc.perform(get("/honor")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].title", is(honor.getTitle())));
  }

  @Test
  public void testGet() throws Exception {
    given(honorRepo.findById(1)).willReturn(Optional.of(honor));
    given(honorRepo.findById(2)).willReturn(Optional.empty());

    mvc.perform(get("/honor/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title", is(honor.getTitle()))); //making sure getting the right data

    //checking when id does not exist (findById returns empty optional)
    mvc.perform(get("/honor/2"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Honor not Found")));
  }

  @Test
  public void testPost() throws Exception {
    given(honorRepo.save(Mockito.any())).willReturn(honor);

    mvc.perform(post("/honor")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(honor)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title", is(honor.getTitle())));
  }

  @Test
  void testDelete() throws Exception {
    given(honorRepo.findById(1)).willReturn(Optional.of(honor));
    given(honorRepo.findById(2)).willReturn(Optional.empty());

    mvc.perform(delete("/honor/1"))
      .andDo(print())
      .andExpect(status().isOk());

    //checking when id does not exist (findById returns empty optional)
    mvc.perform(delete("/honor/2"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Honor not Found")));
  }

  @Test
  void testUpdate() throws Exception {
    given(honorRepo.findById(1)).willReturn(Optional.of(honor));
    given(honorRepo.findById(2)).willReturn(Optional.empty());

    Honor newHonor = new Honor("Updated title", "updated description", "Updated dateReceived", "Updated receivedFrom");
    newHonor.setId(2);

    //checking when id does not exist (findById returns empty optional)
    mvc.perform(put("/honor")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newHonor)))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Honor not Found")));

    newHonor.setId(1);

    given(honorRepo.save(Mockito.any())).willReturn(newHonor);
   
    mvc.perform(put("/honor")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newHonor)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title", is(newHonor.getTitle())));
  }

  @Test
  void testGetByPortfolioId() throws Exception {
    Portfolio portfolio = new Portfolio(1, "new portfolio", new User(1, "test" , "user", "test@email.com" , "password", false), false, false, false, "");
    honor.setPortfolio(portfolio);
    List<Honor> allHonors = Arrays.asList(honor);
  
    given(honorRepo.findByPortfolio(portfolio)).willReturn(allHonors);
    given(portfolioRepo.findById(1)).willReturn(Optional.of(portfolio));
    given(portfolioRepo.findById(2)).willReturn(Optional.empty());

    mvc.perform(get("/honor/portfolio/1"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].title", is(honor.getTitle())))
      .andExpect(jsonPath("$[0].portfolio.id", is(portfolio.getId())));
    
    // test for portfolio not found
    mvc.perform(get("/honor/portfolio/2"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Portfolio not Found")));
    
    portfolio.setId(3);
    allHonors = new ArrayList<Honor>();
    given(honorRepo.findByPortfolio(portfolio)).willReturn(allHonors);
    given(portfolioRepo.findById(3)).willReturn(Optional.of(portfolio));

    // test for honor not found with a found portfolio
    mvc.perform(get("/honor/portfolio/3"))
      .andDo(print())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$", hasSize(0)));
  }
}
