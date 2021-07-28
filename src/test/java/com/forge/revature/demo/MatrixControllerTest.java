package com.forge.revature.demo;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.MatrixController;
import com.forge.revature.models.Matrix;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Skill;
import com.forge.revature.models.User;
import com.forge.revature.repo.MatrixRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.SkillRepo;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MatrixController.class)
public class MatrixControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MatrixRepo matrixRepo;

	@MockBean
	private SkillRepo skillRepo;
	
	@MockBean
	private PortfolioRepo portRepo;

	private Matrix matrix;

	private Portfolio portfolio;

	private List<Skill> skills;

	@BeforeEach
	public void setup() {
		portfolio = new Portfolio(1, "Tom\'s Portfolio", new User(), true, true, true, "Everything looks good.");
		matrix = new Matrix("Languages");
		matrix.setPortfolio(portfolio);
		skills = new ArrayList<>();
		skills.add(new Skill("Java", 9, matrix));
		skills.add(new Skill("Python", 3, matrix));
		skills.add(new Skill("COBOL", 6, matrix));
		matrix.setSkills(skills);
		matrix.setId(1);
	}

	@Test
	public void testGetAll() throws Exception {
		List<Matrix> allMatrices = Arrays.asList(matrix);

		given(matrixRepo.findAll()).willReturn(allMatrices);

		mvc.perform(get("/matrix").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].header", is(matrix.getHeader())));
	}

	@Test
	public void testGetById() throws Exception {
		given(matrixRepo.findById(1)).willReturn(Optional.of(matrix));
		given(matrixRepo.findById(2)).willReturn(Optional.empty());

		mvc.perform(get("/matrix/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.header", is(matrix.getHeader())));

		// test if getting a matrix that doesn't exist
		mvc.perform(get("/matrix/2"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string(containsString("Matrix Not Found")));
	}

	@Test
	public void testGetByPortfolio() throws Exception {
		
		List<Matrix> allMatrices = Arrays.asList(matrix);
		
		given(matrixRepo.findAllByPortfolio(portfolio)).willReturn(allMatrices);
		given(portRepo.findById(1)).willReturn(Optional.of(portfolio));

		mvc.perform(get("/matrix/portfolio/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].header", is(matrix.getHeader())));
	}

	@Test
	public void testPost() throws Exception {
		
		given(matrixRepo.save(matrix)).willReturn(matrix);
		given(skillRepo.saveAll(skills)).willReturn(skills);
		
		mvc.perform(post("/matrix")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(matrix)))
			.andDo(print())
			.andExpect(status().isOk());
	  }
	
	@Test
	public void testUpdate() throws Exception {

		given(matrixRepo.save(matrix)).willReturn(matrix);
		given(matrixRepo.findById(1)).willReturn(Optional.of(matrix));
		given(skillRepo.saveAll(skills)).willReturn(skills);
		given(skillRepo.findAllByMatrix(matrix)).willReturn(skills);

		mvc.perform(put("/matrix")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(matrix)))
			.andDo(print())
			.andExpect(status().isOk());
		
	  }
}
