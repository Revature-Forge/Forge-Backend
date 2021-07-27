package modelTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.Portfolio;
import com.forge.revature.models.WorkHistory;

@SpringBootTest(classes=WorkHistory.class)
class WorkHistoryTests {

	WorkHistory full = new WorkHistory(22, "name", "employer", "responsibilities", "description", "tools", "07-27-2021", "07-27-2021", null);
	WorkHistory empty = new WorkHistory();
	
//test Constructors
	@Test
	void testConstructors() {
		WorkHistory whn = new WorkHistory();
		WorkHistory wh1 = new WorkHistory("name", "employer", "responsibilities", "description", "tools", "07-27-2021", "07-27-2021");
		WorkHistory wh2 = new WorkHistory("name", "employer", "responsibilities", "description", "tools", "07-27-2021", "07-27-2021", null);
		WorkHistory wh3 = new WorkHistory(22, "name", "employer", "responsibilities", "description", "tools", "07-27-2021", "07-27-2021", null);
		
		assertNull(wh3.getPortfolio());
		assertSame("employer", wh2.getEmployer());
		assertSame("tools", wh1.getTools());
		assertEquals(0, whn.getId());
	}
	
//test Getters
	
	@Test
	void getIdTest() {
		assertEquals(22, full.getId());
	}
	
	@Test
	void getTitleTest() {
		assertSame("name", full.getTitle());
	}
	
	@Test
	void getEmployerTest() {
		assertSame("employer", full.getEmployer());
	}
	
	@Test
	void getResponsibilitiesTest() {
		assertSame("responsibilities", full.getResponsibilities());
	}
	
	@Test
	void getDescriptionTest() {
		assertSame("description", full.getDescription());
	}
	
	@Test
	void getToolsTest() {
		assertSame("tools", full.getTools());
	}
	
	@Test
	void getStartDateTest() {
		assertSame("07-27-2021", full.getStartDate());
	}
	
	@Test
	void getEndDateTest() {
		assertSame("07-27-2021", full.getEndDate());
	}
	
	@Test
	void getPortfolioTest() {
		assertNull(full.getPortfolio());
	}
	
	//setters
	@Test
	void setIdTest() {
		empty.setId(22);
		assertEquals(22, empty.getId());
	}
	
	@Test
	void setTitleTest() {
		empty.setTitle("name");
		assertSame("name", empty.getTitle());
	}
	
	@Test
	void setEmployerTest() {
		empty.setEmployer("employer");
		assertSame("employer", empty.getEmployer());
	}
	
	@Test
	void setResponsibilitiesTest() {
		empty.setResponsibilities("responsibilities");
		assertSame("responsibilities", empty.getResponsibilities());
	}
	
	@Test
	void setDescriptionTest() {
		empty.setDescription("description");
		assertSame("description", empty.getDescription());
	}
	
	@Test
	void setToolsTest() {
		empty.setTools("tools");
		assertSame("tools", empty.getTools());
	}
	
	@Test
	void setStartDateTest() {
		empty.setStartDate("07-27-2021");
		assertSame("07-27-2021", empty.getStartDate());
	}
	
	@Test
	void setEndDateTest() {
		empty.setEndDate("07-27-2021");
		assertSame("07-27-2021", full.getEndDate());
	}
	
	@Test
	void setPortfolioTest() {
		Portfolio pf = new Portfolio(1, "Test", null, false, false, false, "");
		empty.setPortfolio(pf);
		assertNotNull(empty.getPortfolio());
	}
	
	
}
