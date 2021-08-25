package com.forge.revature.controllers;


import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.forge.revature.models.*;
import com.forge.revature.repo.*;
import com.forge.revature.services.PortfolioService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/portfolios")
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioController {
	
	@Autowired
	PortfolioService portfolioService;
	
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
    
    @Autowired
    MatrixRepo matrixRepo;
    
    @Autowired
    SkillRepo skillRepo;

    public PortfolioController(PortfolioRepo portRepo) {
        this.portRepo = portRepo;
    }

    @GetMapping
    public List<Portfolio> getAll(){
    	return portfolioService.getAll();
    }

    @GetMapping("/{id}")
    public Portfolio getByID(@PathVariable(name = "id") int id){
        return portfolioService.getByID(id);
    }

    @GetMapping("/users/all/{id}")
    public List<Portfolio> getPortfoliosByUserId(@PathVariable int id){
    	return portfolioService.getPortfoliosByUserId(id);
    }

    @PostMapping
    public Portfolio postPort(@RequestBody Portfolio port){
        return portfolioService.postPort(port);
    }
    @PostMapping("/{id}")
    public void updateUser(@PathVariable int id , @RequestBody Portfolio updated){
    	portfolioService.updateUser(id, updated);
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public Map<String, Boolean> deletePortfolio(@PathVariable int id) throws ResourceNotFoundException{
    	return portfolioService.deletePortfolio(id);
    }

    @GetMapping(value = "/full/{id}", produces = "application/octet-stream")
    public ResponseEntity<ByteArrayResource> getFullPortfolio(@PathVariable int id, HttpServletResponse response) throws JsonProcessingException {
    	return portfolioService.getFullPortfolio(id, response);
    }

    @Transactional
    @PostMapping(value = "/full", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void postFullPortfolio(@RequestBody FullPortfolio fullPortfolio){
    	portfolioService.postFullPortfolio(fullPortfolio);
    }
	
	/**
	 * 
	 * @param max is the list of matrices to be serialized
	 * @return the list with each matrix having its list of skills inserted
	 */
	public List<Matrix> insertSkills(List<Matrix> max) {
		return portfolioService.insertSkills(max);
	}

	/**
	 * 
	 * @param listMax is the list of deserialized matrices
	 * @return the list of all the matrices' skills with each Matrix field set for SQL storage
	 */
	public List<Skill> extractSkills(List<Matrix> listMax) {
		return portfolioService.extractSkills(listMax);
	}
}