package modelTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.Equivalency;
import com.forge.revature.models.Portfolio;

@SpringBootTest(classes=Equivalency.class)
class EquivalencyTests {

@Test
void constructorsTest() {
	Equivalency e1 = new Equivalency("Java", 16);
	
	Portfolio p = new Portfolio(1, "test", null, false, false, false, null);
	Equivalency e2 = new Equivalency("SQL", 7, p);
	
	assertEquals(16, e1.getValue());
	assertSame("test", e2.getPortfolio().getName());
}

@Test
void toStringTest() {
	Portfolio p = new Portfolio(1, "test", null, false, false, false, null);
	Equivalency e = new Equivalency(1, "SQL", 7, p);
	assertNotNull(e.toString());
	assertTrue(e.toString().length() > 0);
}

}
