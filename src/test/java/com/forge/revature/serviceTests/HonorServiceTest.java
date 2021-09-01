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
import com.forge.revature.models.Honor;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.repo.HonorRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.services.HonorService;

@SpringBootTest
public class HonorServiceTest {

  HonorService honorService;
  
  @MockBean
  HonorRepo honorRepo;

  @MockBean
  PortfolioRepo portfolioRepo;

  Honor honor;

  @BeforeEach
  public void setup() {
	this.honorService = new HonorService(honorRepo, portfolioRepo);
    this.honor = new Honor("Developer of the Year", "Top Performing Developer", "2019", "Revature");
    this.honor.setId(1);
  }

  @Test
  public void testGet() throws Exception {
    given(honorRepo.findById(2)).willReturn(Optional.empty());

    //checking when id does not exist (findById returns empty optional)
	assertThrows(NotFoundException.class, ()->honorService.getHonor(2));
  }

  @Test
  void testDelete() throws Exception {
    given(honorRepo.findById(2)).willReturn(Optional.empty());

    //checking when id does not exist (findById returns empty optional)
	assertThrows(NotFoundException.class, ()->honorService.deleteHonor(2));
  }

  @Test
  void testUpdate() throws Exception {
    given(honorRepo.findById(2)).willReturn(Optional.empty());

    Honor newHonor = new Honor("Updated title", "updated description", "Updated dateReceived", "Updated receivedFrom");
    newHonor.setId(2);

    //checking when id does not exist (findById returns empty optional)
	assertThrows(NotFoundException.class, ()->honorService.updateHonor(newHonor));
  }

  @Test
  void testGetByPortfolioId() throws Exception {
	HashMap<String, String> map = new HashMap<>();
    Portfolio portfolio = new Portfolio(1, "new portfolio", new User(1, "test" , "user", "test@email.com" , "password", false), false, false, false, "", map);
    honor.setPortfolio(portfolio);
    List<Honor> allHonors = Arrays.asList(honor);
  
    given(honorRepo.findByPortfolio(portfolio)).willReturn(allHonors);
    given(portfolioRepo.findById(2)).willReturn(Optional.empty());

    // test for portfolio not found
	assertThrows(NotFoundException.class, ()->honorService.getByPortfolioId(2));

	// setup new environment
    portfolio.setId(3);
    allHonors = new ArrayList<Honor>();
    given(honorRepo.findByPortfolio(portfolio)).willReturn(allHonors);
    given(portfolioRepo.findById(3)).willReturn(Optional.of(portfolio));

    // test for honor not found with a found portfolio
	assertDoesNotThrow(()->honorService.getByPortfolioId(3));
  }
}