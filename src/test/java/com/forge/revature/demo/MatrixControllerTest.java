package com.forge.revature.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.forge.revature.controllers.MatrixController;
import com.forge.revature.models.Matrix;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Skill;
import com.forge.revature.models.User;
import com.forge.revature.repo.MatrixRepo;
import com.forge.revature.repo.SkillRepo;

@SpringBootTest
public class MatrixControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MatrixRepo matrixRepo;

	@MockBean
	private SkillRepo skillRepo;

	private Matrix matrix;

	private Portfolio portfolio;

	private List<Skill> skills;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.standaloneSetup(new MatrixController(matrixRepo, skillRepo)).build();
		portfolio = new Portfolio(1, "Tom\'s Portfolio", new User(), true, true, true, "Everything looks good.");
		matrix = new Matrix("Languages", this.portfolio);
		skills = new ArrayList<>();
		skills.add(new Skill("Java", 6, matrix));
		skills.add(new Skill("Python", 3, matrix));
		skills.add(new Skill("COBOL", 6, matrix));
		matrix.setSkills(skills);
		matrix.setId(1);
	}

	@Test
	public void testGetAll() throws Exception {
		List<Matrix> allMatrices = Arrays.asList(matrix);

		given(matrixRepo.findAll()).willReturn(allMatrices);

		mvc.perform(get("/matrix").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].header", is(matrix.getHeader())))
				.andExpect(jsonPath("$[0].id", is(matrix.getId())));
	}

	@Test
	public void testGetById() throws Exception {
		given(matrixRepo.findById(1)).willReturn(Optional.of(matrix));

		mvc.perform(get("/matrix/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.header", is(matrix.getHeader())))
				.andExpect(jsonPath("$.id", is(matrix.getId())));

		// test if getting a matrix that doesn't exist
		mvc.perform(get("/matrix/2")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	public void testGetByPortfolio() throws Exception {
		given(matrixRepo.findById(1)).willReturn(Optional.of(matrix));

		mvc.perform(get("/matrix/portfolio/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.header", is(matrix.getHeader())))
				.andExpect(jsonPath("$.id", is(matrix.getId())));

		mvc.perform(get("/matrix/portfolio/2")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	public void testPost() throws Exception {
		List<Matrix> allMatrices = Arrays.asList(matrix);

		given(matrixRepo.findAll()).willReturn(allMatrices);

		mvc.perform(post("/matrix").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].header", is(matrix.getHeader())))
				.andExpect(jsonPath("$[0].id", is(matrix.getId())));
	}
}
