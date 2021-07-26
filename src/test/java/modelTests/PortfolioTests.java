package modelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;

@SpringBootTest(classes=Portfolio.class)
public class PortfolioTests {

	Portfolio p = new Portfolio();
	Portfolio fullP = new Portfolio(1, "Test", null, true, true, true, "");
	
	@Test
	void testConstructor() {
		Portfolio p1 = new Portfolio();
		Portfolio p2 = new Portfolio("Test", false, true, true, "none");
		Portfolio p3 = new Portfolio(1, "Test", null, true, true, true, "");
		
		assertTrue(p1.getId() == 0);
		assertTrue(p2.getName() == "Test");
		assertTrue(p3.getId() == 1);
	}
	
	@Test
	void testGetId() {
		assertEquals(fullP.getId(), 1);
	}
	
	@Test
	void testGetName() {
		assertEquals(fullP.getName(), "Test");
	}
	
	@Test
	void testGetUser() {
		assertNull(fullP.getUser());
	}
	
	@Test
	void testIsSubmitted() {
		assertTrue(fullP.isSubmitted());
	}
	
	@Test
	void testIsReviewed() {
		assertTrue(fullP.isReviewed());
	}
	
	@Test
	void testIsApproved() {
		assertTrue(fullP.isApproved());
	}
	
	@Test
	void testGetFeedback() {
		assertEquals(fullP.getFeedback(), "");
	}
	
	@Test
	void testSetId() {
		p.setId(17);
		assertTrue(p.getId() == 17);
	}
	
	@Test
	void testSetUser() {
		User u = new User();
		u.setFName("mockito!");
		p.setUser(u);
		
		assertEquals(p.getUser().getFName(), "mockito!");
	}
	
	@Test
	void testSetSubmitted() {
		p.setSubmitted(true);
		assertTrue(p.isSubmitted());
	}
	
	@Test
	void testSetReviewed() {
		p.setReviewed(false);
		assertTrue(!p.isReviewed());
	}
	
	@Test
	void testSetApproved() {
		p.setApproved(true);
		assertTrue(p.isApproved());
	}
	
	@Test
	void testSetFeedback() {
		p.setFeedback("feedback");
		assertEquals(p.getFeedback(), "feedback");
	}
}
