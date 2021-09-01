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
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.models.WorkHistory;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.WorkHistoryRepo;
import com.forge.revature.services.WorkHistoryService;

@SpringBootTest
public class WorkHistoryServiceTest {
  
  private WorkHistoryService workHistroyService;
  
  @MockBean
  private WorkHistoryRepo workHistoryRepo;

  @MockBean
  private PortfolioRepo portfolioRepo;

  private WorkHistory workHistory;

  @BeforeEach
  public void setup() {
	  workHistroyService = new WorkHistoryService(workHistoryRepo, portfolioRepo);
	  workHistory = new WorkHistory("Scrum Master", "Amazon", "Leading team meetings", "In charge of all scrum meetings", "Java", "May 20, 2010", "March 13, 2021");
	  workHistory.setId(1);
  }

  @Test
  public void testGet() throws Exception {
    given(workHistoryRepo.findById(2)).willReturn(Optional.empty());

    //checking when getting a work history with invalid id
    assertThrows(NotFoundException.class, () -> workHistroyService.getWorkHistory(2));
  }

  @Test
  void testDelete() throws Exception {
    given(workHistoryRepo.findById(2)).willReturn(Optional.empty());

    //checking when deleting a work history with invalid id
    assertThrows(NotFoundException.class, () -> workHistroyService.deleteWorkHistory(2));
  }

  @Test
  void testUpdate() throws Exception {
    given(workHistoryRepo.findById(2)).willReturn(Optional.empty());

    WorkHistory newGit = new WorkHistory("Scrum Master", "Google", "Leading team meetings", "In charge of all scrum meetings", "Java", "May 20, 2010", "March 13, 2021");
    
    newGit.setId(2);

  //checking when id does not exist (findById returns empty optional)
    assertThrows(NotFoundException.class, () -> workHistroyService.updateWorkHistory(newGit));
  }

  @Test
  void testGetByPortfolioId() throws Exception {
	HashMap<String, String> map = new HashMap<>();
    Portfolio portfolio = new Portfolio(1, "new portfolio", new User(1, "test" , "user", "test@email.com" , "password", false), false, false, false, "", map);
    workHistory.setPortfolio(portfolio);
    List<WorkHistory> allWorkHistory = Arrays.asList(workHistory);
  
    given(workHistoryRepo.findByPortfolio(portfolio)).willReturn(allWorkHistory);
    given(portfolioRepo.findById(2)).willReturn(Optional.empty());
    
    
    // test for workhistory not found
    assertThrows(NotFoundException.class, () -> workHistroyService.getByPortfolioId(2));

    // setup new environment
    portfolio.setId(3);
    allWorkHistory = new ArrayList<WorkHistory>();
    given(workHistoryRepo.findByPortfolio(portfolio)).willReturn(allWorkHistory);
    given(portfolioRepo.findById(3)).willReturn(Optional.of(portfolio));    
    
    // test for workhistory not found with a found portfolio
    assertDoesNotThrow(() -> workHistroyService.getByPortfolioId(3));
  }

}
