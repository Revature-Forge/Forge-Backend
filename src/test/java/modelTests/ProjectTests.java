package modelTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.Project;

@SpringBootTest(classes=Project.class)
class ProjectTests {

@Test
void testConstructors() {
	Project p2 = new Project("name", "description", "responsibilities", "techonologies", "url", "product");
	Project p3 = new Project("name", "description", "responsibilities", "techonologies", "url", "product", null);
	
	assertSame("description", p2.getDescription());
	assertNull(p3.getPortfolio());
}

@Test
void testToString() {
	Project p3 = new Project(1, "name", "description", "responsibilities", "techonologies", "url", "product", null);
	assertNotNull(p3.toString());
	assertTrue(p3.toString().length() > 0);
}

}
