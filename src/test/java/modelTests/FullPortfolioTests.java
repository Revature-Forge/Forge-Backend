package modelTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.FullPortfolio;

@SpringBootTest(classes=FullPortfolio.class)
class FullPortfolioTests {

@Test
void constructorTest() {
//	this.name = name;
//	this.submitted = submitted;
//	this.approved = approved;
//	this.reviewed = reviewed;
//	this.feedback = feedback;
//	this.aboutMe = aboutMe;
//	this.certifications = certifications;
//	this.educations = educations;
//	this.equivalencies = equivalencies;
//	this.gitHubs = gitHubs;
//	this.honors = honors;
//	this.projects = projects;
//	this.workExperiences = workExperiences;
//	this.workHistories = workHistories;
	
	FullPortfolio p = new FullPortfolio("test", true, false, false, "feeback", null, null, null, null, null, null, null, null, null);
	assertSame("feedback", p.getFeedback());
}

@Test
void toStringTest() {
	FullPortfolio p = new FullPortfolio("test", true, false, false, "feeback", null, null, null, null, null, null, null, null, null);
	assertNotNull(p.toString());
	assertTrue(p.toString().length() > 0);
}
}
