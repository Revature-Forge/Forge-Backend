package com.forge.revature.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.forge.revature.models.GitHub;
import com.forge.revature.services.GitHubService;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/github")
@AllArgsConstructor
public class GitHubController {
	
	private GitHubService gitHubService;
	
	@GetMapping
	public List<GitHub> getAll() {
		return gitHubService.getAll();
	}
	
	@GetMapping("/{id}")
	public GitHub getGitHub(@PathVariable int id) {
		return gitHubService.getGitHub(id);
	}
	
	@PostMapping
	public GitHub postGitHub(@RequestBody GitHub gitHub) {
		return gitHubService.postGitHub(gitHub);
	}
	
	@PutMapping
	public GitHub updateGitHub(@RequestBody GitHub updateGit) {
		return gitHubService.updateGitHub(updateGit);
	}
	
	@DeleteMapping("/{id}")
	public void deleteGitHub(@PathVariable int id) {
		gitHubService.deleteGitHub(id);
	}
	
	@GetMapping("/portfolio/{id}")
	public List<GitHub> getByPortfolioId(@PathVariable int id) {
		return gitHubService.getByPortfolioId(id);
	}
	
	@GetMapping("/portfolio/all/{id}")
	public List<GitHub> getPortfolioGitHubs(@PathVariable(name = "id") int id) {
		return gitHubService.getPortfolioGitHubs(id);
	}
}