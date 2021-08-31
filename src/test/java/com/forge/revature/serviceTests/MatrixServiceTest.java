package com.forge.revature.serviceTests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.exception.NotFoundException;
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
class MatrixServiceTest {

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

		assertTrue(matrixService.getAll().size() > 0);
	}

	@Test
	void testGetById() throws Exception {
		given(matrixRepo.findById(1)).willReturn(Optional.of(matrix));
		given(matrixRepo.findById(2)).willReturn(Optional.empty());

		assertEquals(matrix, matrixService.getById(1));

		// test if getting a matrix that doesn't exist
		assertThrows(NotFoundException.class, ()->matrixService.getById(2));
	}

	@Test
	void testGetByPortfolio() throws Exception {

		List<Matrix> allMatrices = Arrays.asList(matrix);
		
		given(matrixRepo.findAllByPortfolio(portfolio)).willReturn(allMatrices);
		given(portRepo.findById(1)).willReturn(Optional.of(portfolio));

		assertEquals(allMatrices, matrixService.getByPortfolio(1));
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

		assertEquals(matrix, matrixService.putSkill(1, sd));
		
		assertEquals(matrix, matrixService.putSkill(1, sd1));
	  }
}
