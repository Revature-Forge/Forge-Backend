package modelTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.FullPortfolio;

@SpringBootTest(classes = FullPortfolio.class)
class FullPortfolioTests {

	@Test
	void constructorTest() {

		FullPortfolio p = new FullPortfolio(0, "test", null, true, false, false, "feedback", null, null, null, null,
				null, null, null, null, null, null);
		assertSame("feedback", p.getFeedback());
	}
	
	@Test
	void constructorTest2() {
		FullPortfolio p = new FullPortfolio();
		assertSame(null, p.getFeedback());
	}
	
	@Test
	void constructorTest3() {
		FullPortfolio p = new FullPortfolio("test", null, true, false, false, "feedback", null, null, null, null,
				null, null, null, null, null, null);
		assertSame("feedback", p.getFeedback());
	}
	
	@Test
	void toStringTest() {
		FullPortfolio p = new FullPortfolio(0, "test", null, true, false, false, "feedback", null, null, null, null,
				null, null, null, null, null, null);
		assertNotNull(p.toString());
		assertTrue(p.toString().length() > 0);
	}

}
