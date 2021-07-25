package com.forge.revature.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.forge.revature.controllers.PortfolioController;
import com.forge.revature.models.*;
import com.forge.revature.repo.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


@SpringBootTest
public class PortfolioTest {
    private MockMvc mvc;

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
    
    @MockBean
    MatrixRepo matrixRepo;
    
    @MockBean
    SkillRepo skillRepo;
    
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .standaloneSetup(new PortfolioController(repo, aboutMeRepo, certificationRepo, educationRepo, equivalencyRepo,
                    gitHubRepo, honorRepo, projectRepo, workExperienceRepo, workHistoryRepo, matrixRepo, skillRepo))
            .build();
    }

    @Test
    void testGetall() throws Exception {
        System.out.println(repo);
        given(repo.findAll())
            .willReturn(new ArrayList<Portfolio>());

        mvc.perform(get("/portfolios"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }

    @Test
    void testGetById() throws Exception{
        given(repo.findById(1)).willReturn(Optional.of(new Portfolio()));


        mvc.perform(get("/portfolios/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
    }
    @Test
    void testGetAllByUserId() throws Exception{
        given(repo.findAllByUserId(1)).willReturn(new ArrayList<Portfolio>());

        mvc.perform(get("/portfolios/users/all/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    void testPost() throws Exception{
        Portfolio port = new Portfolio(1, "new portfilio", new User(1, "test", "user" , "test@email.com" , "password", false), false, false, false, "");
        
        given(repo.save(port)).willReturn(port);

        mvc.perform(post("/portfolios")
            .contentType("application/json;charset=utf-8")
            .content(new ObjectMapper().writeValueAsString(port)))

            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();
    }

    void testUpdate() throws Exception{
        Portfolio port = new Portfolio(1, "new portfilio", new User(1, "test", "user" , "test@email.com" , "password", false), false, false, false, "");
        Portfolio port2 = new Portfolio(1, "new portfilio name", new User(1, "test", "user" , "test@email.com" , "password", false), true, true, true, "feedback");
        Optional<Portfolio> returned = Optional.of(port);
        

        given(repo.findById(1)).willReturn(returned);

        mvc.perform(post("/portfolios/1")
            .contentType("application/json")
            .content(new ObjectMapper().writeValueAsString(port2)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
    }

    @Test
    void testdelete() throws Exception {
        Portfolio port = new Portfolio(1, "new portfilio", new User(1, "test", "user" , "test@email.com" , "password", false), false, false, false, "");
        Optional<Portfolio> returned = Optional.of(port);

        given(repo.findById(1)).willReturn(returned);

        mvc.perform(delete("/portfolios/1"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetFullPortfolio() throws Exception {
        Optional<Portfolio> port = Optional.of(new Portfolio(1, "new portfolio",
                new User(1, "test", "user" , "test@email.com" , "password", false), false, false, false, ""));
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
        project.add(new Project("name", "description", "responsibilities", "technologies", "repositoryUrl", port.get()));
        given(projectRepo.findByPortfolio_Id(1)).willReturn(project);

        ArrayList<WorkExperience> experience = new ArrayList<>();
        experience.add(new WorkExperience("employer", "title", "responsibilities", "description", "technologies", 
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-24"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-05"), port.get()));
        given(workExperienceRepo.findByPortfolio_Id(1)).willReturn(experience);

        ArrayList<WorkHistory> history = new ArrayList<>();
        history.add(new WorkHistory(1, "title", "employer", "responsibilities", "description", "tools", "startDate", "endDate", port.get()));
        given(workHistoryRepo.findByPortfolio(port.get())).willReturn(history);

        mvc.perform(get("/portfolios/full/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/octet-stream"))
            .andReturn();
    }

    @Test
    public void testPostFullPortfolioWithJSON() throws Exception{
<<<<<<< HEAD
        //Create test data
        Date dateForTest = new Date();
        User testUser = new User();
        Portfolio testPortfolio = new Portfolio();
        AboutMe testAboutMe = new AboutMe();
        //Create certifications list
        Certification testCertification1 = new Certification("Test 1", "123", "Testing", dateForTest, "TestURL");
        Certification testCertification2 = new Certification("Test 2", "321", "Testing2", dateForTest, "TestURL2");
        List<Certification> testCertifications = new ArrayList<>();
        testCertifications.add(testCertification1);
        testCertifications.add(testCertification2);
        //Create educations list
        Education testEducation = new Education("Test University", "Degree", "A date", 0, "Testurl");
        List<Education> testEducationList = new ArrayList<>();
        testEducationList.add(testEducation);
        //Create equivalency list
        Equivalency testEquivalency = new Equivalency("Test", 0, testPortfolio);
        List<Equivalency> testEquivalenciesList = new ArrayList<>();
        testEquivalenciesList.add(testEquivalency);
        //Create github data
        GitHub testGitHub = new GitHub("Test GitHub", "testurl");
        List<GitHub> testGitHubList = new ArrayList<>();
        testGitHubList.add(testGitHub);
        //Create honor list
        Honor testHonor = new Honor("Test Honor", "Test honor description", "Test date", "Received from test");
        List<Honor> testHonorList = new ArrayList<>();
        testHonorList.add(testHonor);
        //Create Project list
        Project testProject = new Project("Project Name", "Project Description", "Project Responsibilities", "Project technologies", "Testurl");
        List<Project> testProjectsList = new ArrayList<>();
        testProjectsList.add(testProject);
        //Create workexperience list
        WorkExperience testWorkExperience = new WorkExperience("Test employer", "Test Title", "Test responsibilities", "Test description", "Test technologies", dateForTest, dateForTest);
        List<WorkExperience> testWorkExperiences = new ArrayList<>();
        testWorkExperiences.add(testWorkExperience);
        //Create workhistory list
        WorkHistory testWorkHistory = new WorkHistory("Test title", "Test employer", "Test responsibilities", "Test description", "test tools", "StartDate test", "Enddate test");
        List<WorkHistory> testWorkHistoriesList = new ArrayList<>();
        testWorkHistoriesList.add(testWorkHistory);
        //Create full portfolio
        FullPortfolio testFullPortfolio = new FullPortfolio(0, "Tester", testUser, false, false, false, "Test Feedback", testAboutMe, testCertifications, testEducationList, testEquivalenciesList, testGitHubList, testHonorList, testProjectsList, testWorkExperiences, testWorkHistoriesList, null);
=======
        ObjectMapper objectMapper = new ObjectMapper();
        User u = new User(0, "fname", "", "", "", false);

        ObjectNode portfolio = objectMapper.createObjectNode();
        portfolio.put("name", "");
        portfolio.put("submitted", false);
        portfolio.put("approved", false);
        portfolio.put("reviewed", false);
        portfolio.put("feedback", "");
>>>>>>> 02501c92b5c9f7a28d6bdbaba2dc50fa86baf5d3
        
        System.out.println(portfolio);

        ObjectNode aboutMe = objectMapper.createObjectNode();
        aboutMe.put("bio", "bio");
        aboutMe.put("email", "email");
        aboutMe.put("phone", "phone");

        List<ObjectNode> certifications = new ArrayList<>();
        ObjectNode cert = objectMapper.createObjectNode();
        cert.put("name", "");
        cert.put("certId", "");
        cert.put("issuedBy", "");
        cert.put("issuedOn", 0);
        cert.put("publicUrl", "");
        certifications.add(cert);

        List<ObjectNode> educations = new ArrayList<>();
        ObjectNode edu = objectMapper.createObjectNode();
        edu.put("university", "");
        edu.put("degree", "");
        edu.put("graduationDate", "2021-07-15");
        edu.put("gpa", 0.0);
        edu.put("logoUrl", "");
        educations.add(edu);

        List<ObjectNode> equivalencies = new ArrayList<>();
        ObjectNode equiv = objectMapper.createObjectNode();
        equiv.put("header", "header");
        equiv.put("value", 0);
        equivalencies.add(equiv);

        List<ObjectNode> honors = new ArrayList<>();
        ObjectNode honor = objectMapper.createObjectNode();
        honor.put("title", "");
        honor.put("description", "");
        honor.put("dateReceived", "2021-07-15");
        honor.put("receivedFrom", "");
        honors.add(honor);

        List<ObjectNode> projects = new ArrayList<>();
        ObjectNode proj = objectMapper.createObjectNode();
        proj.put("name", "");
        proj.put("description", "");
        proj.put("responsibilities", "");
        proj.put("technologies", "");
        proj.put("workProducts", "");
        projects.add(proj);

        List<ObjectNode> workExperiences = new ArrayList<>();
        ObjectNode workExp = objectMapper.createObjectNode();
        workExp.put("employer", "");
        workExp.put("title", "");
        workExp.put("responsibilities", "");
        workExp.put("description", "");
        workExp.put("technologies", "");
        workExp.put("startDate", 1625374000);
        workExp.put("endDate", 1625374000);
        workExperiences.add(workExp);

        List<ObjectNode> workHistories = new ArrayList<>();
        ObjectNode workHistory = objectMapper.createObjectNode();
        workHistory.put("title", "");
        workHistory.put("employer", "");
        workHistory.put("responsibilities", "");
        workHistory.put("description", "");
        workHistory.put("tools", "");
        workHistory.put("startDate", "2021-07-08");
        workHistory.put("endDate", "2021-07-08");
        workHistories.add(workHistory);

        portfolio.putPOJO("certifications", certifications);
        portfolio.putPOJO("educations", educations);
        portfolio.putPOJO("equivalencies", equivalencies);
        portfolio.putPOJO("githubs", new ArrayList<ObjectNode>());
        portfolio.putPOJO("honors", honors);
        portfolio.putPOJO("projects", projects);
        portfolio.putPOJO("workExperiences", workExperiences);
        portfolio.putPOJO("workHistories", workHistories);

        mvc.perform(post("/portfolios/full")
                .contentType(MediaType.APPLICATION_JSON)
                .sessionAttr("user", u)
                .content(portfolio.toString()))
                .andExpect(status().isOk());
    }
}
