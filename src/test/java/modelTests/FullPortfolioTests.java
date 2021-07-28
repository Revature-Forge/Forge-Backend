package modelTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.FullPortfolio;

@SpringBootTest(classes=FullPortfolio.class)
class FullPortfolioTests {

@Test
void constructorTest() {
	
	FullPortfolio p = new FullPortfolio("test", true, false, false, "feedback", null, null, null, null, null, null, null, null, null);
	assertSame("feedback", p.getFeedback());
}

@Test
void toStringTest() {
	FullPortfolio p = new FullPortfolio("test", true, false, false, "feedback", null, null, null, null, null, null, null, null, null);
	assertNotNull(p.toString());
	assertTrue(p.toString().length() > 0);
}
}
