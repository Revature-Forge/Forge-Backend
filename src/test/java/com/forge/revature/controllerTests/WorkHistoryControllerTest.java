package com.forge.revature.controllerTests;

import com.forge.revature.controllers.WorkHistoryController;
import com.forge.revature.repo.WorkHistoryRepo;
import com.forge.revature.services.WorkHistoryService;
import com.forge.revature.models.WorkHistory;
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
@WebMvcTest(WorkHistoryController.class)
public class WorkHistoryControllerTest {
  @Autowired
  private MockMvc mvc;

  private static String baseUrl = "/api/workhistory";
  
  @MockBean
  private WorkHistoryController workHistoryController;
  
  @MockBean
  private WorkHistoryRepo workHistoryRepo;

  @MockBean
  private PortfolioRepo portfolioRepo;

  private WorkHistory workHistory;

  @BeforeEach
  public void setup() {
	  this.workHistoryController = new WorkHistoryController(new WorkHistoryService(workHistoryRepo, portfolioRepo));
	  this.workHistory = new WorkHistory("Scrum Master", "Amazon", "Leading team meetings", "In charge of all scrum meetings", "Java", "May 20, 2010", "March 13, 2021");
	  this.workHistory.setId(1);
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
    given(workHistoryRepo.findById(2)).willReturn(Optional.empty());

    mvc.perform(get(baseUrl + "/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title", is(workHistory.getTitle()))); //making sure getting the right data

    //checking when id does not exist (findById returns empty optional)
    mvc.perform(get(baseUrl + "/2"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("WorkHistory not Found")));
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
    given(workHistoryRepo.findById(2)).willReturn(Optional.empty());

    mvc.perform(delete(baseUrl + "/1"))
      .andDo(print())
      .andExpect(status().isOk());

    //checking when id does not exist (findById returns empty optional)
    mvc.perform(delete(baseUrl + "/2"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("WorkHistory not Found")));
  }

  @Test
  void testUpdate() throws Exception {
    given(workHistoryRepo.findById(1)).willReturn(Optional.of(workHistory));
    given(workHistoryRepo.findById(2)).willReturn(Optional.empty());

    WorkHistory newGit = new WorkHistory("Scrum Master", "Google", "Leading team meetings", "In charge of all scrum meetings", "Java", "May 20, 2010", "March 13, 2021");
    newGit.setId(2);

    //checking when id does not exist (findById returns empty optional)
    mvc.perform(put(baseUrl)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newGit)))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("WorkHistory not Found")));
    
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
    given(portfolioRepo.findById(2)).willReturn(Optional.empty());

    mvc.perform(get(baseUrl + "/portfolio/1"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].title", is(workHistory.getTitle())))
      .andExpect(jsonPath("$[0].portfolio.id", is(portfolio.getId())));
    
    // test for workhistory not found
    mvc.perform(get(baseUrl + "/portfolio/2"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Portfolio not Found")));
    
    portfolio.setId(3);
    allWorkHistory = new ArrayList<WorkHistory>();
    given(workHistoryRepo.findByPortfolio(portfolio)).willReturn(allWorkHistory);
    given(portfolioRepo.findById(3)).willReturn(Optional.of(portfolio));

    // test for workhistory not found with a found portfolio
    mvc.perform(get(baseUrl + "/portfolio/3"))
      .andDo(print())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$", hasSize(0)));
  }

}
