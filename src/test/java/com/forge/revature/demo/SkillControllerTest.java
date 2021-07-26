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

import com.forge.revature.controllers.SkillController;
import com.forge.revature.models.Matrix;
import com.forge.revature.models.Skill;
import com.forge.revature.repo.MatrixRepo;
import com.forge.revature.repo.SkillRepo;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SkillController.class)
public class SkillControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private MatrixRepo matrixRepo;

	@MockBean
	private SkillRepo skillRepo;
	
	private Matrix matrix;
	
	private Skill skill1;
	
	private Skill skill2;
	
	private Skill skill3;
	
	private List<Skill> skills;
	
	@BeforeEach
	public void setup() {
		matrix = new Matrix("Languages");
		matrix.setId(1);
		skill1 = new Skill(1, "Java", 9, matrix);
		skill2 = new Skill(2, "Python", 3, matrix);
		skill3 = new Skill(3, "COBOL", 6, matrix);
		skills = new ArrayList<>();
		skills.add(skill1);
		skills.add(skill2);
		skills.add(skill3);
	}

}
