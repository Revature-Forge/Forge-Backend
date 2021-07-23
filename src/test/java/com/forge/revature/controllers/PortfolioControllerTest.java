package com.forge.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.forge.revature.DemoApplication;
import com.forge.revature.models.*;
import com.forge.revature.repo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class PortfolioControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PortfolioRepo portRepo;

    @Autowired
    AboutMeRepo aboutMeRepo;

    @Autowired
    CertificationRepo certificationRepo;

    @Autowired
    EducationRepo educationRepo;

    @Autowired
    EquivalencyRepo equivalencyRepo;

    @Autowired
    GitHubRepo gitHubRepo;

    @Autowired
    HonorRepo honorRepo;

    @Autowired
    ProjectRepo projectRepo;

    @Autowired
    WorkExperienceRepo workExperienceRepo;

    @Autowired
    WorkHistoryRepo workHistoryRepo;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testValidJsonUpload() throws Exception {
        User u = new User(0, "fname", "", "", "", false);

        ObjectNode portfolio = objectMapper.createObjectNode();
        portfolio.put("name", "");
        portfolio.put("submitted", false);
        portfolio.put("approved", false);
        portfolio.put("reviewed", false);
        portfolio.put("feedback", "");

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

        mvc.perform(post("/portfolios/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .sessionAttr("user", u)
                .content(portfolio.toString()))
                .andExpect(status().isOk());
    }
}