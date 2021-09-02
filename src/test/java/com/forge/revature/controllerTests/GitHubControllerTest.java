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
import com.forge.revature.controllers.GitHubController;
import com.forge.revature.models.GitHub;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.repo.GitHubRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.services.GitHubService;

@SpringBootTest
public class GitHubControllerTest {
  private MockMvc mvc;
  
  private static String baseUrl = "/api/github";

  private GitHubController gitHubController;
  
  private GitHubService gitHubService;
  
  @MockBean
  private GitHubRepo gitHubRepo;

  @MockBean
  private PortfolioRepo portfolioRepo;

  private GitHub gitHub;

  @BeforeEach
  public void setup() {
	  gitHubService = new GitHubService(gitHubRepo, portfolioRepo);
	  gitHubController = new GitHubController(gitHubService);
	  mvc = MockMvcBuilders.standaloneSetup(gitHubController).build();
	  gitHub = new GitHub("www.github.com/user", "profile pic");
	  gitHub.setId(1);
  }

  @Test
  public void testGetAll() throws Exception {
    List<GitHub> allGitHub = Arrays.asList(gitHub);
  
    given(gitHubRepo.findAll()).willReturn(allGitHub);

    mvc.perform(get(baseUrl)
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].url", is(gitHub.getUrl())));
  }

  @Test
  public void testGet() throws Exception {
    given(gitHubRepo.findById(1)).willReturn(Optional.of(gitHub));

    mvc.perform(get(baseUrl + "/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.url", is(gitHub.getUrl()))); //making sure getting the right data
  }

  @Test
  public void testPost() throws Exception {
    given(gitHubRepo.save(Mockito.any())).willReturn(gitHub);

    mvc.perform(post(baseUrl)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(gitHub)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.url", is(gitHub.getUrl())));
  }

  @Test
  void testDelete() throws Exception {
    given(gitHubRepo.findById(1)).willReturn(Optional.of(gitHub));

    mvc.perform(delete(baseUrl + "/1"))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  void testUpdate() throws Exception {
    given(gitHubRepo.findById(1)).willReturn(Optional.of(gitHub));

    GitHub newGit = new GitHub("www.github.com/updatedUser", "updated profile pic");
    newGit.setId(1);

    given(gitHubRepo.save(Mockito.any())).willReturn(newGit);
   
    mvc.perform(put(baseUrl)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newGit)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.url", is(newGit.getUrl())));
  }

  @Test
  void testGetByPortfolioId() throws Exception {
	  HashMap<String, String> map = new HashMap<>();
    Portfolio portfolio = new Portfolio(1, "new portfolio", new User(1, "test" , "user", "test@email.com" , "password", false), false, false, false, "", map);
    gitHub.setPortfolio(portfolio);
    List<GitHub> allGitHubs = Arrays.asList(gitHub);

    given(gitHubRepo.findByPortfolio(portfolio)).willReturn(allGitHubs);
    given(portfolioRepo.findById(1)).willReturn(Optional.of(portfolio));

    mvc.perform(get(baseUrl + "/portfolio/1"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].url", is(gitHub.getUrl())))
      .andExpect(jsonPath("$[0].portfolio.id", is(portfolio.getId())));
  }
}