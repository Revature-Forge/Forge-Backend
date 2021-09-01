package com.forge.revature.serviceTests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.forge.revature.exception.NotFoundException;
import com.forge.revature.models.GitHub;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.repo.GitHubRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.services.GitHubService;

@SpringBootTest
public class GitHubServiceTest {
  
  private GitHubService gitHubService;
  
  @MockBean
  private GitHubRepo gitHubRepo;

  @MockBean
  private PortfolioRepo portfolioRepo;

  private GitHub gitHub;

  @BeforeEach
  public void setup() {
	  gitHubService = new GitHubService(gitHubRepo, portfolioRepo);
	  gitHub = new GitHub("www.github.com/user", "profile pic");
  }

  @Test
  public void testGet() throws Exception {
    given(gitHubRepo.findById(2)).willReturn(Optional.empty());

    //checking when id does not exist (findById returns empty optional)
	assertThrows(NotFoundException.class, ()->gitHubService.getGitHub(2));
  }

  @Test
  void testDelete() throws Exception {
    given(gitHubRepo.findById(2)).willReturn(Optional.empty());

    //checking when id does not exist (findById returns empty optional)
	assertThrows(NotFoundException.class, ()->gitHubService.deleteGitHub(2));
  }

  @Test
  void testUpdate() throws Exception {
    given(gitHubRepo.findById(2)).willReturn(Optional.empty());

    GitHub newGit = new GitHub("www.github.com/updatedUser", "updated profile pic");
    newGit.setId(2);

    //checking when id does not exist (findById returns empty optional)
	assertThrows(NotFoundException.class, ()->gitHubService.updateGitHub(newGit));
  }

  @Test
  void testGetByPortfolioId() throws Exception {
	HashMap<String, String> map = new HashMap<>();
    Portfolio portfolio = new Portfolio(1, "new portfolio", new User(1, "test" , "user", "test@email.com" , "password", false), false, false, false, "", map);
    gitHub.setPortfolio(portfolio);
    List<GitHub> allGitHubs = Arrays.asList(gitHub);

    given(gitHubRepo.findByPortfolio(portfolio)).willReturn(allGitHubs);
    given(portfolioRepo.findById(2)).willReturn(Optional.empty());

    // test for portfolio not found
	assertThrows(NotFoundException.class, ()->gitHubService.getByPortfolioId(2));
    
    // setup new environment
	portfolio.setId(3);
    allGitHubs = new ArrayList<GitHub>();
    given(gitHubRepo.findByPortfolio(portfolio)).willReturn(allGitHubs);
    given(portfolioRepo.findById(3)).willReturn(Optional.of(portfolio));

    // test for github not found with a found portfolio
	assertDoesNotThrow(()->gitHubService.getByPortfolioId(3));
  }
}
