package modelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.Matrix;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Skill;

@SpringBootTest(classes=Skill.class)
class SkillTests {
	Portfolio pf;
	Matrix fullMatrix;
	Skill skill;
	Skill fullSkill;
	List<Skill> skills;
		
	@BeforeEach
	void setup() {
		pf = new Portfolio(1, "Test", null, false, false, false, "", null);
		fullMatrix = new Matrix("Languages", pf);
		fullMatrix.setId(1);
		skill = new Skill();
		fullSkill = new Skill(1, "Java", 24, fullMatrix);
	}
	
	@Test
	void getIdTest() {
		assertEquals(1, fullSkill.getId());
	}

	@Test
	void getNameTest() {
		assertSame("Java", fullSkill.getName());
	}

	@Test
	void getValueTest() {
		assertEquals(24, fullSkill.getValue());
	}
	
	@Test
	void getMatrixTest() {
		assertEquals(1, fullSkill.getMatrix().getId());
	}

	@Test
	void setIdTest() {
		skill.setId(45);
		assertEquals(45, skill.getId());
	}

	@Test
	void setNameTest() {
		skill.setName("This is a test");
		assertSame("This is a test", skill.getName());
	}
	
	@Test
	void setValueTest() {
		skill.setValue(45);
		assertEquals(45, skill.getValue());
	}
	
	@Test
	void setMatrixTest() {
		skill.setMatrix(fullMatrix);
		assertSame("Languages", skill.getMatrix().getHeader());
	}
}
