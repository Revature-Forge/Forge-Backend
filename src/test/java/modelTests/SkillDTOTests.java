package modelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.forge.revature.models.SkillDTO;

class SkillDTOTests {
	
	SkillDTO fullSkill = new SkillDTO(1, "Java", 24);
	
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
	void constructorTest() {
		SkillDTO skill = new SkillDTO(0, "SQL", 6);
		assertEquals(0, skill.getId());
		assertSame("SQL", skill.getName());
		assertEquals(6, skill.getValue());
	}

}
