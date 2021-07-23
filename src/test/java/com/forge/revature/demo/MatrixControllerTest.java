package com.forge.revature.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.forge.revature.controllers.MatrixController;
import com.forge.revature.models.Matrix;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Skill;
import com.forge.revature.models.User;
import com.forge.revature.repo.MatrixRepo;
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
	}
}
