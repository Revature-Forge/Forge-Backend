package com.forge.revature.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.forge.revature.models.WorkExperience;
import com.forge.revature.services.WorkExperienceService;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/workexperience")
@AllArgsConstructor
public class WorkExperienceController {
   
	private WorkExperienceService workExperienceService;
	
    @GetMapping
    public ResponseEntity<List<WorkExperience>> allExperience() {
        return workExperienceService.allExperience();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<WorkExperience>> getExperience(@PathVariable(name = "id") long id) {
    	return workExperienceService.getExperience(id);
    }

    @GetMapping("/portfolio/{id}")
    public ResponseEntity<List<WorkExperience>> getPortfolio(@PathVariable(name = "id") int id) {
    	return workExperienceService.getPortfolio(id);
    }

    @PostMapping
    public void createExperience(@RequestBody WorkExperience work) {
    	workExperienceService.createExperience(work);
    }

    @PostMapping("/{id}")
    public void updateExperience(@PathVariable(name = "id") long id, @RequestBody WorkExperience work) {
    	workExperienceService.updateExperience(id, work);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteExperience(@PathVariable(name = "id") long id) {
    	workExperienceService.deleteExperience(id);
    }

    @GetMapping("/portfolio/all/{id}")
    public List<WorkExperience> getPortfolioWorkExperiences(@PathVariable(name = "id") int portfolioId) {
    	return workExperienceService.getPortfolioWorkExperiences(portfolioId);
    }
}
