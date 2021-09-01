package com.forge.revature.serviceTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.forge.revature.models.AdminChart;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.UserRepo;
import com.forge.revature.services.AdminChartService;
import com.forge.revature.services.PortfolioService;

@SpringBootTest
public class AdminChartServiceTest {

	@MockBean
	private UserRepo userRepo;

	@MockBean
	private PortfolioRepo portfolioRepo;
	
	private AdminChartService adminChartService;
	
	private PortfolioService portfolioService;
	
	private User user;

	private Portfolio portfolio;
	
	private AdminChart adminChart;
	
	private List<User> allAdmin;
	
	@BeforeEach
	public void setup() {
		adminChartService = new AdminChartService(userRepo, portfolioRepo, portfolioService);
		user = new User(1, "Hong", "Wu", "hong@mail.com", "password", true);
		portfolio = new Portfolio(1, "hong Portfolio", user, true, true, true, user, "description", true, true, "", "", 0L, null);
		adminChart = new AdminChart("Hong Wu", 1, 0);
		allAdmin = new ArrayList<User>();
		allAdmin.add(user);
	}

	@Test
	public void getApproveCountTest() {
		given(portfolioRepo.getApproveCount(1)).willReturn(1);
		
		assertEquals(1, adminChartService.getCount(true, 1));
	}
	
	@Test
	public void getDeniedCountTest() {
		given(portfolioRepo.getDeniedCount(1)).willReturn(0);
		assertEquals(0, adminChartService.getCount(false, 1));		
	}
	
	@Test
	public void getAdminListTest() throws Exception {
		given(userRepo.findAllByAdmin(true)).willReturn(allAdmin);
		
		assertEquals("Hong", adminChartService.getAdminList().get(0).getFName());
	}
}