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
import com.forge.revature.models.Project;
import com.forge.revature.services.ProjectService;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/projects")
@AllArgsConstructor
public class ProjectController {
	
	private ProjectService projectService;
	
	public ResponseEntity<List<Project>> allExperience() {
		return projectService.allExperience();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Project>> getExperience(@PathVariable(name = "id") long id) {
		return projectService.getExperience(id);
	}
	
	@GetMapping("/portfolio/{id}")
	public ResponseEntity<List<Project>> portfolioExperience(@PathVariable(name = "id") int id) {
		return projectService.portfolioExperience(id);
	}
	
	@PostMapping
	public void createExperience(@RequestBody Project proj) {
		projectService.createExperience(proj);
	}
	
	@PostMapping("/{id}")
	public void updateExperience(@PathVariable(name = "id") long id, @RequestBody Project proj) {
		projectService.updateExperience(id, proj);
	}
	
	@DeleteMapping("/{id}")
	public void deleteExperience(@PathVariable(name = "id") long id) {
		projectService.deleteExperience(id);
	}
	
	@GetMapping("/portfolio/all/{id}")
	public List<Project> getPortfolioProjects(@PathVariable(name = "id") int portfolioId) {
		return projectService.getPortfolioProjects(portfolioId);
	}
}