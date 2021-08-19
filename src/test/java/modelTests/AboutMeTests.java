package modelTests;

import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.AboutMe;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.forge.revature.models.Portfolio;

@SpringBootTest(classes = AboutMe.class)
class AboutMeTests {

	Portfolio pf = new Portfolio(1, "Test", null, false, false, false, "", null, null);
	AboutMe me = new AboutMe();
	AboutMe fullMe = new AboutMe(1, pf, "bio", "test@test.com", "1234567890");

@Test
void getIdTest() {
	assertEquals(1, fullMe.getId());
}

@Test
void getPortfolioTest() {
	assertEquals(1, fullMe.getPortfolio().getId());
}

@Test
void getBioTest() {
	assertSame("bio", fullMe.getBio());
}

@Test
void getEmailTest() {
	assertSame("test@test.com", fullMe.getEmail());
}

@Test
void getPhoneTest() {
	assertSame("1234567890", fullMe.getPhone());
}

@Test
void setIdTest() {
	me.setId(45);
	assertEquals(45, me.getId());
}

@Test
void setPortfolioTest() {
	me.setPortfolio(pf);
	assertSame("Test", me.getPortfolio().getName());
}

@Test
void setBioTest() {
	me.setBio("This is a test");
	assertSame("This is a test", me.getBio());
}

@Test
void setEmailTest() {
	me.setEmail("email@email.com");
	assertSame("email@email.com", me.getEmail());
}

@Test
void setPhoneTest() {
	me.setPhone("8675309");
	assertSame("8675309", me.getPhone());
}

@Test
void testToString() {
	assertNotNull(fullMe.toString());
	assertTrue(fullMe.toString().length() > 0);
}

}
