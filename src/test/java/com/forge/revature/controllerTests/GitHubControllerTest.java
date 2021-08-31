package com.forge.revature.controllerTests;

import com.forge.revature.controllers.GitHubController;
import com.forge.revature.repo.GitHubRepo;
import com.forge.revature.models.GitHub;
import com.forge.revature.models.Portfolio;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.services.GitHubService;
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
@WebMvcTest(GitHubController.class)
public class GitHubControllerTest {
  @Autowired
  private MockMvc mvc;
  
  private static String baseUrl = "/api/github";

  @MockBean
  private GitHubController gitHubController;
  
  @MockBean
  private GitHubRepo gitHubRepo;

  @MockBean
  private PortfolioRepo portfolioRepo;

  private GitHub gitHub;

  @BeforeEach
  public void setup() {
	  this.gitHubController = new GitHubController(new GitHubService(gitHubRepo, portfolioRepo));
	  this.gitHub = new GitHub("www.github.com/user", "profile pic");
	  this.gitHub.setId(1);
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
    given(gitHubRepo.findById(2)).willReturn(Optional.empty());

    mvc.perform(get(baseUrl + "/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.url", is(gitHub.getUrl()))); //making sure getting the right data

    //checking when id does not exist (findById returns empty optional)
    mvc.perform(delete(baseUrl + "/2"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("GitHub not Found")));
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
    given(gitHubRepo.findById(2)).willReturn(Optional.empty());

    mvc.perform(delete(baseUrl + "/1"))
      .andDo(print())
      .andExpect(status().isOk());

    //checking when id does not exist (findById returns empty optional)
    mvc.perform(delete(baseUrl + "/2"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("GitHub not Found")));
  }

  @Test
  void testUpdate() throws Exception {
    given(gitHubRepo.findById(1)).willReturn(Optional.of(gitHub));
    given(gitHubRepo.findById(2)).willReturn(Optional.empty());

    GitHub newGit = new GitHub("www.github.com/updatedUser", "updated profile pic");
    newGit.setId(2);

    //checking when id does not exist (findById returns empty optional)
    mvc.perform(put(baseUrl)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newGit)))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("GitHub not Found")));

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
    given(portfolioRepo.findById(2)).willReturn(Optional.empty());

    mvc.perform(get(baseUrl + "/portfolio/1"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].url", is(gitHub.getUrl())))
      .andExpect(jsonPath("$[0].portfolio.id", is(portfolio.getId())));
    
    // test for portfolio not found
    mvc.perform(get(baseUrl + "/portfolio/2"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Portfolio not Found")));
    
    portfolio.setId(3);
    allGitHubs = new ArrayList<GitHub>();
    given(gitHubRepo.findByPortfolio(portfolio)).willReturn(allGitHubs);
    given(portfolioRepo.findById(3)).willReturn(Optional.of(portfolio));

    // test for github not found with a found portfolio
    mvc.perform(get(baseUrl + "/portfolio/3"))
      .andDo(print())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$", hasSize(0)));
  }
}
