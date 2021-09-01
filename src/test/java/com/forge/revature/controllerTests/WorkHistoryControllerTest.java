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
import com.forge.revature.controllers.WorkHistoryController;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.models.WorkHistory;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.WorkHistoryRepo;
import com.forge.revature.services.WorkHistoryService;

@SpringBootTest
public class WorkHistoryControllerTest {

  private MockMvc mvc;

  private static String baseUrl = "/api/workhistory";
  

  private WorkHistoryController workHistoryController;
  
  private WorkHistoryService workHistroyService;
  
  @MockBean
  private WorkHistoryRepo workHistoryRepo;

  @MockBean
  private PortfolioRepo portfolioRepo;

  private WorkHistory workHistory;

  @BeforeEach
  public void setup() {
	  workHistroyService = new WorkHistoryService(workHistoryRepo, portfolioRepo);
	  workHistoryController = new WorkHistoryController(workHistroyService);
	  this.mvc = MockMvcBuilders.standaloneSetup(workHistoryController).build();
	  workHistory = new WorkHistory("Scrum Master", "Amazon", "Leading team meetings", "In charge of all scrum meetings", "Java", "May 20, 2010", "March 13, 2021");
	  workHistory.setId(1);
  }

  @Test
  public void testGetAll() throws Exception {
    List<WorkHistory> allWorkHistory = Arrays.asList(workHistory);
  
    given(workHistoryRepo.findAll()).willReturn(allWorkHistory);

    mvc.perform(get(baseUrl)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].title", is(workHistory.getTitle())));
  }

  @Test
  public void testGet() throws Exception {
    given(workHistoryRepo.findById(1)).willReturn(Optional.of(workHistory));

    mvc.perform(get(baseUrl + "/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title", is(workHistory.getTitle()))); //making sure getting the right data
  }

  @Test
  public void testPost() throws Exception {
    given(workHistoryRepo.save(Mockito.any())).willReturn(workHistory);

    mvc.perform(post(baseUrl)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(workHistory)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title", is(workHistory.getTitle())));
  }

  @Test
  void testDelete() throws Exception {
    given(workHistoryRepo.findById(1)).willReturn(Optional.of(workHistory));

    mvc.perform(delete(baseUrl + "/1"))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  void testUpdate() throws Exception {
    given(workHistoryRepo.findById(1)).willReturn(Optional.of(workHistory));

    WorkHistory newGit = new WorkHistory("Scrum Master", "Google", "Leading team meetings", "In charge of all scrum meetings", "Java", "May 20, 2010", "March 13, 2021");
    
    newGit.setId(1);

    given(workHistoryRepo.save(Mockito.any())).willReturn(newGit);

    mvc.perform(put(baseUrl)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newGit)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title", is(newGit.getTitle())));
  }

  @Test
  void testGetByPortfolioId() throws Exception {
	  HashMap<String, String> map = new HashMap<>();
    Portfolio portfolio = new Portfolio(1, "new portfolio", new User(1, "test" , "user", "test@email.com" , "password", false), false, false, false, "", map);
    workHistory.setPortfolio(portfolio);
    List<WorkHistory> allWorkHistory = Arrays.asList(workHistory);
  
    given(workHistoryRepo.findByPortfolio(portfolio)).willReturn(allWorkHistory);

    given(portfolioRepo.findById(1)).willReturn(Optional.of(portfolio));

    mvc.perform(get(baseUrl + "/portfolio/1"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].title", is(workHistory.getTitle())))
      .andExpect(jsonPath("$[0].portfolio.id", is(portfolio.getId())));
  }

}
