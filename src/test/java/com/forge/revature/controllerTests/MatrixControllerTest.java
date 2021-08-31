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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.MatrixController;
import com.forge.revature.models.Matrix;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Skill;
import com.forge.revature.models.SkillDTO;
import com.forge.revature.models.User;
import com.forge.revature.repo.MatrixRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.SkillRepo;
import com.forge.revature.services.MatrixService;

@SpringBootTest
public class MatrixControllerTest {
	
	private MockMvc mvc;
	
	private MatrixController matrixController;
	
	private MatrixService matrixService;

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
	void setup() {
		matrixService = new MatrixService(matrixRepo, skillRepo, portRepo);
		matrixController = new MatrixController(matrixService);
		this.mvc = MockMvcBuilders.standaloneSetup(matrixController).build();
		
		portfolio = new Portfolio(1, "Tom\'s Portfolio", new User(), true, true, true, "Everything looks good.", null);
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
	void testGetAll() throws Exception {
		List<Matrix> allMatrices = Arrays.asList(matrix);

		given(matrixRepo.findAll()).willReturn(allMatrices);

		mvc.perform(get("/api/matrix").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].header", is(matrix.getHeader())));
	}

	@Test
	void testGetById() throws Exception {
		given(matrixRepo.findById(1)).willReturn(Optional.of(matrix));
		given(matrixRepo.findById(2)).willReturn(Optional.empty());

		mvc.perform(get("/api/matrix/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.header", is(matrix.getHeader())));
	}

	@Test
	void testGetByPortfolio() throws Exception {
		
		List<Matrix> allMatrices = Arrays.asList(matrix);
		
		given(matrixRepo.findAllByPortfolio(portfolio)).willReturn(allMatrices);
		given(portRepo.findById(1)).willReturn(Optional.of(portfolio));

		mvc.perform(get("/api/matrix/portfolio/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].header", is(matrix.getHeader())));
	}

	@Test
	void testPost() throws Exception {
		
		given(matrixRepo.save(matrix)).willReturn(matrix);
		given(skillRepo.saveAll(skills)).willReturn(skills);
		
		mvc.perform(post("/api/matrix")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(matrix)))
			.andDo(print())
			.andExpect(status().isOk());
	  }
	
	@Test
	void testUpdate() throws Exception {
		Matrix matrix2 = new Matrix("Languages");
		matrix2.setPortfolio(portfolio);
		given(matrixRepo.save(matrix2)).willReturn(matrix2);
		given(matrixRepo.save(matrix)).willReturn(matrix);
		given(matrixRepo.findById(0)).willReturn(Optional.empty());
		given(matrixRepo.findById(1)).willReturn(Optional.of(matrix));
		given(skillRepo.saveAll(skills)).willReturn(skills);
		given(skillRepo.findAllByMatrix(matrix)).willReturn(skills);

		mvc.perform(put("/api/matrix")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(matrix)))
			.andDo(print())
			.andExpect(status().isOk());
		
		mvc.perform(put("/api/matrix")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(matrix2)))
				.andDo(print())
				.andExpect(status().isOk());
	  }
	
	@Test
	void testDelete() throws Exception {
		given(matrixRepo.findById(6)).willReturn(Optional.of(matrix));
		mvc.perform(delete("/api/matrix/6"))
			.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateSkill() throws Exception {
		SkillDTO sd = new SkillDTO(0, "SQL", 6);
		SkillDTO sd1 = new SkillDTO(1, "SQL", 6);
		Skill skill = new Skill(1, "SQL", 6, matrix);

		given(matrixRepo.findById(1)).willReturn(Optional.of(matrix));
		given(skillRepo.saveAndFlush(skill)).willReturn(skill);
		given(skillRepo.findById(1)).willReturn(Optional.of(skill));
		given(skillRepo.findAllByMatrix(matrix)).willReturn(skills);

		mvc.perform(put("/api/matrix/1/skill")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(sd)))
			.andDo(print())
			.andExpect(status().isOk());
		
		mvc.perform(put("/api/matrix/1/skill")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(sd1)))
				.andDo(print())
				.andExpect(status().isOk());
	  }
	
	@Test
	void testDeleteSkill() throws Exception {
		Skill skill = new Skill(6, "SQL", 6, matrix);
		given(skillRepo.findById(6)).willReturn(Optional.of(skill));
		mvc.perform(delete("/api/matrix/skill/6"))
			.andExpect(status().isOk());
	}
}
