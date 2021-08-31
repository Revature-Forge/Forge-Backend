package com.forge.revature.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.forge.revature.models.WorkExperience;
import com.forge.revature.repo.WorkExperienceRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkExperienceService {
	
	WorkExperienceRepo repo;
	
	public ResponseEntity<List<WorkExperience>> allExperience() {
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<Optional<WorkExperience>> getExperience(long id) {
		return new ResponseEntity<>(repo.findById(id), HttpStatus.OK);
	}
	
	public ResponseEntity<List<WorkExperience>> getPortfolio(int id) {
		return new ResponseEntity<>(repo.findByPortfolio_Id(id), HttpStatus.OK);
	}
	
	public void createExperience(WorkExperience work) {
		repo.save(work);
	}
	
	public void updateExperience(long id, WorkExperience work) {
		Optional<WorkExperience> update = repo.findById(id);
	
		if (update.isPresent()) {
			update.get().setEmployer(work.getEmployer());
			update.get().setTitle(work.getTitle());
			update.get().setResponsibilities(work.getResponsibilities());
			update.get().setDescription(work.getDescription());
			update.get().setTechnologies(work.getTechnologies());
			update.get().setStartDate(work.getStartDate());
			update.get().setEndDate(work.getEndDate());
			update.get().setPortfolio(work.getPortfolio());
	
			work = update.get();
			repo.save(work);
		}
	}
	
	public void deleteExperience(long id) {
		repo.deleteById(id);
	}
	
	public List<WorkExperience> getPortfolioWorkExperiences(int portfolioId) {
		List<WorkExperience> retrievedWorkExperiences = repo.findAllByPortfolioId(portfolioId);
	
		return retrievedWorkExperiences;
	}
}
