package modelTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.Matrix;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Skill;

@SpringBootTest(classes = Matrix.class)
class MatrixTests {
	
	Portfolio pf;
	Matrix matrix;
	Matrix fullMatrix;
	Skill skill1;
	Skill skill2;
	Skill skill3;
	List<Skill> skills;
		
	@BeforeEach
	void setup() {
		pf = new Portfolio(1, "Test", null, false, false, false, "", null);
		matrix =  new Matrix();
		fullMatrix = new Matrix("Languages", pf);
		fullMatrix.setId(1);
		skill1 = new Skill(1, "Java", 24, fullMatrix);
		skill2 = new Skill(2, "Python", 12, fullMatrix);
		skill3 = new Skill(3, "Java", 24, fullMatrix);
		skills = new ArrayList<>();
		skills.add(skill1);
		skills.add(skill2);
		skills.add(skill3);
		fullMatrix.setSkills(skills);
	}
	
	@Test
	void getIdTest() {
		assertEquals(1, fullMatrix.getId());
	}

	@Test
	void getPortfolioTest() {
		assertEquals(1, fullMatrix.getPortfolio().getId());
	}

	@Test
	void getHeaderTest() {
		assertSame("Languages", fullMatrix.getHeader());
	}

	@Test
	void getSkillsTest() {
		assertSame(skills, fullMatrix.getSkills());
	}

	@Test
	void setIdTest() {
		matrix.setId(45);
		assertEquals(45, matrix.getId());
	}

	@Test
	void setPortfolioTest() {
		matrix.setPortfolio(pf);
		assertSame("Test", matrix.getPortfolio().getName());
	}

	@Test
	void setHeaderTest() {
		matrix.setHeader("This is a test");
		assertSame("This is a test", matrix.getHeader());
	}
	
	@Test
	void setSkillsTest() {
		matrix.setSkills(skills);
		assertSame(skills, matrix.getSkills());
	}

}
