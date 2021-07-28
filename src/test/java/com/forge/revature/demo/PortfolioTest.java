package com.forge.revature.demo;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.PortfolioController;
import com.forge.revature.models.AboutMe;
import com.forge.revature.models.Certification;
import com.forge.revature.models.Education;
import com.forge.revature.models.Equivalency;
import com.forge.revature.models.FullPortfolio;
import com.forge.revature.models.GitHub;
import com.forge.revature.models.Honor;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Project;
import com.forge.revature.models.User;
import com.forge.revature.models.WorkExperience;
import com.forge.revature.models.WorkHistory;
import com.forge.revature.repo.AboutMeRepo;
import com.forge.revature.repo.CertificationRepo;
import com.forge.revature.repo.EducationRepo;
import com.forge.revature.repo.EquivalencyRepo;
import com.forge.revature.repo.GitHubRepo;
import com.forge.revature.repo.HonorRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.ProjectRepo;
import com.forge.revature.repo.UserRepo;
import com.forge.revature.repo.WorkExperienceRepo;
import com.forge.revature.repo.WorkHistoryRepo;

@SpringBootTest
public class PortfolioTest {
	private MockMvc mvc;

	@MockBean
	UserRepo userRepo;

	@MockBean
	PortfolioRepo repo;

	@MockBean
	AboutMeRepo aboutMeRepo;

	@MockBean
	CertificationRepo certificationRepo;

	@MockBean
	EducationRepo educationRepo;

	@MockBean
	EquivalencyRepo equivalencyRepo;

	@MockBean
	GitHubRepo gitHubRepo;

	@MockBean
	HonorRepo honorRepo;

	@MockBean
	ProjectRepo projectRepo;

	@MockBean
	WorkExperienceRepo workExperienceRepo;

	@MockBean
	WorkHistoryRepo workHistoryRepo;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
				.standaloneSetup(new PortfolioController(repo, aboutMeRepo, certificationRepo, educationRepo,
						equivalencyRepo, gitHubRepo, honorRepo, projectRepo, workExperienceRepo, workHistoryRepo))
				.build();
	}

	@Test
	void testGetall() throws Exception {
		System.out.println(repo);
		given(repo.findAll()).willReturn(new ArrayList<Portfolio>());

		mvc.perform(get("/portfolios")).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andReturn();
	}

	@Test
	void testGetById() throws Exception {
		given(repo.findById(1)).willReturn(Optional.of(new Portfolio()));

		mvc.perform(get("/portfolios/1")).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andReturn();
	}

	@Test
	void testGetAllByUserId() throws Exception {
		given(repo.findAllByUserId(1)).willReturn(new ArrayList<Portfolio>());

		mvc.perform(get("/portfolios/users/all/1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	void testPost() throws Exception {
		HashMap<String, String> map = new HashMap<>();
		Portfolio port = new Portfolio(1, "new portfilio",
				new User(1, "test", "user", "test@email.com", "password", false), false, false, false, "", map);

		given(repo.save(port)).willReturn(port);

		mvc.perform(post("/portfolios").contentType("application/json;charset=utf-8")
				.content(new ObjectMapper().writeValueAsString(port)))

				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
	}

	void testUpdate() throws Exception {
		HashMap<String, String> map = new HashMap<>();
		Portfolio port = new Portfolio(1, "new portfilio",
				new User(1, "test", "user", "test@email.com", "password", false), false, false, false, "", map);
		Portfolio port2 = new Portfolio(1, "new portfilio name",
				new User(1, "test", "user", "test@email.com", "password", false), true, true, true, "feedback", map);
		Optional<Portfolio> returned = Optional.of(port);

		given(repo.findById(1)).willReturn(returned);

		mvc.perform(post("/portfolios/1").contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(port2))).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}

	@Test
	void testdelete() throws Exception {
		HashMap<String, String> map = new HashMap<>();
		Portfolio port = new Portfolio(1, "new portfilio",
				new User(1, "test", "user", "test@email.com", "password", false), false, false, false, "", map);
		Optional<Portfolio> returned = Optional.of(port);

		given(repo.findById(1)).willReturn(returned);

		mvc.perform(delete("/portfolios/1")).andExpect(status().isOk());
	}

	@Test
	void testGetFullPortfolio() throws Exception {
		HashMap<String, String> map = new HashMap<>();
		Optional<Portfolio> port = Optional.of(new Portfolio(1, "new portfolio",
				new User(1, "test", "user", "test@email.com", "password", false), false, false, false, "", map));
		given(repo.findById(1)).willReturn(port);
		given(repo.existsById(1)).willReturn(true);

		Optional<AboutMe> aboutMe = Optional.of(new AboutMe(port.get(), "bio", "email", "phone"));
		given(aboutMeRepo.findByPortfolioId(1)).willReturn(aboutMe);

		ArrayList<Certification> certification = new ArrayList<>();
		certification.add(new Certification(port.get(), "name", "certId", "issuedBy",
				new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-24"), "publicUrl"));
		given(certificationRepo.findAllByPortfolioId(1)).willReturn(certification);

		ArrayList<Education> education = new ArrayList<>();
		education.add(new Education(port.get(), "university", "degree", "graduationDate", 3.6, "logoUrl"));
		given(educationRepo.findAllByPortfolioId(1)).willReturn(education);

		ArrayList<Equivalency> equivalency = new ArrayList<>();
		equivalency.add(new Equivalency(1, "header", 5, port.get()));
		given(equivalencyRepo.findAllByPortfolioId(1)).willReturn(equivalency);

		ArrayList<GitHub> github = new ArrayList<>();
		github.add(new GitHub(1, "url", "image", port.get()));
		given(gitHubRepo.findByPortfolio(port.get())).willReturn(github);

		ArrayList<Honor> honor = new ArrayList<>();
		honor.add(new Honor(1, "title", "description", "dateReceived", "receivedFrom", port.get()));
		given(honorRepo.findByPortfolio(port.get())).willReturn(honor);

		ArrayList<Project> project = new ArrayList<>();
		project.add(
				new Project("name", "description", "responsibilities", "technologies", "repositoryUrl", port.get()));
		given(projectRepo.findByPortfolio_Id(1)).willReturn(project);

		ArrayList<WorkExperience> experience = new ArrayList<>();
		experience.add(new WorkExperience("employer", "title", "responsibilities", "description", "technologies",
				new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-24"),
				new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-05"), port.get()));
		given(workExperienceRepo.findByPortfolio_Id(1)).willReturn(experience);

		ArrayList<WorkHistory> history = new ArrayList<>();
		history.add(new WorkHistory(1, "title", "employer", "responsibilities", "description", "tools", "startDate",
				"endDate", port.get()));
		given(workHistoryRepo.findByPortfolio(port.get())).willReturn(history);

		mvc.perform(get("/portfolios/full/1")).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/octet-stream")).andReturn();
	}

	@Test
	public void testPostFullPortfolioWithJSON() throws Exception {
		// Create test data
		Date dateForTest = new Date();
		User testUser = new User();
		Portfolio testPortfolio = new Portfolio();
		AboutMe testAboutMe = new AboutMe();
		// Create certifications list
		Certification testCertification1 = new Certification("Test 1", "123", "Testing", dateForTest, "TestURL");
		Certification testCertification2 = new Certification("Test 2", "321", "Testing2", dateForTest, "TestURL2");
		List<Certification> testCertifications = new ArrayList<>();
		testCertifications.add(testCertification1);
		testCertifications.add(testCertification2);
		// Create educations list
		Education testEducation = new Education("Test University", "Degree", "A date", 0, "Testurl");
		List<Education> testEducationList = new ArrayList<>();
		testEducationList.add(testEducation);
		// Create equivalency list
		Equivalency testEquivalency = new Equivalency("Test", 0, testPortfolio);
		List<Equivalency> testEquivalenciesList = new ArrayList<>();
		testEquivalenciesList.add(testEquivalency);
		// Create github data
		GitHub testGitHub = new GitHub("Test GitHub", "testurl");
		List<GitHub> testGitHubList = new ArrayList<>();
		testGitHubList.add(testGitHub);
		// Create honor list
		Honor testHonor = new Honor("Test Honor", "Test honor description", "Test date", "Received from test");
		List<Honor> testHonorList = new ArrayList<>();
		testHonorList.add(testHonor);
		// Create Project list
		Project testProject = new Project("Project Name", "Project Description", "Project Responsibilities",
				"Project technologies", "Testurl");
		List<Project> testProjectsList = new ArrayList<>();
		testProjectsList.add(testProject);
		// Create workexperience list
		WorkExperience testWorkExperience = new WorkExperience("Test employer", "Test Title", "Test responsibilities",
				"Test description", "Test technologies", dateForTest, dateForTest);
		List<WorkExperience> testWorkExperiences = new ArrayList<>();
		testWorkExperiences.add(testWorkExperience);
		// Create workhistory list
		WorkHistory testWorkHistory = new WorkHistory("Test title", "Test employer", "Test responsibilities",
				"Test description", "test tools", "StartDate test", "Enddate test");
		List<WorkHistory> testWorkHistoriesList = new ArrayList<>();
		testWorkHistoriesList.add(testWorkHistory);
		// Create full portfolio
		HashMap<String, String> map = new HashMap<>();
		FullPortfolio testFullPortfolio = new FullPortfolio(0, "Tester", testUser, false, false, false, "Test Feedback",
				map, testAboutMe, testCertifications, testEducationList, testEquivalenciesList, testGitHubList,
				testHonorList, testProjectsList, testWorkExperiences, testWorkHistoriesList);

//		given(repo.save(Mockito.any(Portfolio.class))).willReturn(new Portfolio(1, "test", u, false, false, false, ""));

		ObjectMapper om = new ObjectMapper();
		mvc.perform(post("/portfolios/full").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(testFullPortfolio))).andExpect(status().isOk());
	}
}
