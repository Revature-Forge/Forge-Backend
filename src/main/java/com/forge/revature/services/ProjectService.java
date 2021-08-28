package com.forge.revature.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.forge.revature.models.Project;
import com.forge.revature.repo.ProjectRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {
	
	ProjectRepo repo;
	
	public ResponseEntity<List<Project>> allExperience() {
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<Optional<Project>> getExperience(long id) {
		return new ResponseEntity<>(repo.findById(id), HttpStatus.OK);
	}
	
	public ResponseEntity<List<Project>> portfolioExperience(int id) {
		return new ResponseEntity<>(repo.findByPortfolio_Id(id), HttpStatus.OK);
	}
	
	public void createExperience(Project proj) {
		repo.save(proj);
	}
	
	public void updateExperience(long id, Project proj) {
		Optional<Project> update = repo.findById(id);
	
		if (update.isPresent()) {
			update.get().setName(proj.getName());
			update.get().setDescription(proj.getDescription());
			update.get().setResponsibilities(proj.getResponsibilities());
			update.get().setTechnologies(proj.getTechnologies());
			update.get().setRespositoryUrl(proj.getRespositoryUrl());
			update.get().setWorkProducts(proj.getWorkProducts());
			update.get().setPortfolio(proj.getPortfolio());
	
			proj = update.get();
			repo.save(proj);
		}
	}
	
	public void deleteExperience(long id) {
		repo.deleteById(id);
	}
	
	public List<Project> getPortfolioProjects(int portfolioId) {
		List<Project> retrievedProjects = repo.findAllByPortfolioId(portfolioId);
	
		return retrievedProjects;
	}
}
