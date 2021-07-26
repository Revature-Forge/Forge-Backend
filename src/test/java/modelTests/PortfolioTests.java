package modelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;

@SpringBootTest(classes=Portfolio.class)
class PortfolioTests {

	Portfolio p = new Portfolio();
	Portfolio fullP = new Portfolio(1, "Test", null, true, true, true, "");
	
	@Test
	void testConstructor() {
		Portfolio p1 = new Portfolio();
		Portfolio p2 = new Portfolio("Test", false, true, true, "none");
		Portfolio p3 = new Portfolio(1, "Test", null, true, true, true, "");
	
		assertEquals(0, p1.getId());
		assertSame("Test", p2.getName());
		assertEquals(1, p3.getId());
	}
	
	@Test
	void testGetId() {
		assertEquals(1, fullP.getId());
	}
	
	@Test
	void testGetName() {
		assertSame("Test", fullP.getName());
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
		assertEquals("", fullP.getFeedback());
	}
	
	@Test
	void testSetId() {
		p.setId(17);
		assertEquals(17, p.getId());
	}
	
	@Test
	void testSetUser() {
		User u = new User();
		u.setFName("mockito!");
		p.setUser(u);
		
		assertEquals("mockito!", p.getUser().getFName());
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
		assertEquals("feedback", p.getFeedback());
	}
}