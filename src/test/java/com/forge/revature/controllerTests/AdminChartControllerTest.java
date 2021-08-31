package com.forge.revature.controllerTests;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.forge.revature.controllers.AdminChartController;
import com.forge.revature.models.AdminChart;
import com.forge.revature.services.AdminChartService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminChartController.class)
public class AdminChartControllerTest {

	@Autowired
	private MockMvc mvc;

	private static String baseUrl = "/api/adminChart";	

	@MockBean
	AdminChartService adminChartService;
	
	private AdminChart adminChart;

	@Test
	public void getAdminWorkReportTest() throws Exception {
		this.adminChart = new AdminChart("Hong Wu", 1, 0);
		List<AdminChart> adminChartList = new ArrayList<AdminChart>();
		adminChartList.add(adminChart);

		given(adminChartService.getAdminWorkReport()).willReturn(ResponseEntity.status(HttpStatus.OK).body(adminChartList));
		
		System.out.println();
				
		mvc.perform(get(baseUrl)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Hong Wu"));
	}
}