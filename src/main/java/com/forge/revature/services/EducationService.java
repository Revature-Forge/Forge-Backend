package com.forge.revature.services;

import java.util.List;
import java.util.Optional;
import com.forge.revature.models.Education;
import com.forge.revature.repo.EducationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Max Lee
 * @version 1.0
 * 
 * Controller for Education. Has CRUD functionality described per method
 * Code moved to Service Layer from Controller Layer by Aaron Killeen
 */
@Service
@AllArgsConstructor
public class EducationService {
	
	EducationRepo educationRepo;
	
	/**
	* Retrieves all educations stored in the database
	* @return List of all Educations in the database in JSON format
	*/
	public List<Education> getAll() {
		List<Education> educations = educationRepo.findAll();
		return educations;
	}
	
	/**
	* Retrieves an education based on the given ID
	* @param id id of the education
	* @return Single education found
	*/
	public Education getEducation(int id) {
		return educationRepo.findById(id).get();
	}
	
	/**
	* Creates a new education in the database
	* @param education new education being created
	* @return the representation of the education with its newly generated primary key.
	*/
	public Education postEducation(Education education) {
		return educationRepo.save(education);
	}
	
	/**
	* Updates the education associated with the id
	* @param newEducation updated information
	* @param educationId ID of the education being updated
	*/
	public void updateEducation(Education newEducation, int educationId) {
		Optional<Education> oldEducation = educationRepo.findById(educationId);
	
		if (oldEducation.isPresent()) {
			oldEducation.get().setUniversity(newEducation.getUniversity());
			oldEducation.get().setDegree(newEducation.getDegree());
			oldEducation.get().setGraduationDate(newEducation.getGraduationDate());
			oldEducation.get().setGpa(newEducation.getGpa());
			oldEducation.get().setLogoUrl(newEducation.getLogoUrl());
	
			educationRepo.save(oldEducation.get());
		}
	}
	
	/**
	* Deletes the associated education
	* @param educationId ID of the education being deleted
	*/
	public void deleteEducation(int educationId) {
		educationRepo.deleteById(educationId);
	}
	
	/**
	* Gets the education based on the user's id
	* @param userId id of the user
	* @return education found to be linked to the user
	*/
	public Education getUserEducation(int userId) {
		Optional<Education> retrievedEducation = educationRepo.findByPortfolioUserId(userId);
	
		if (retrievedEducation.isPresent()) {
			return retrievedEducation.get();
		}
	
		return null;
	}
	
	/**
	* Finds the education based on the portfolio id
	* @param portfolioId ID of the portfolio
	* @return the education found to be linked to the portfolio id
	*/
	public Education getPortfolioEducation(int portfolioId) {
		Optional<Education> retrievedEducation = educationRepo.findByPortfolioId(portfolioId);
	
		if (retrievedEducation.isPresent()) {
			return retrievedEducation.get();
		}
	
		return null;
	}
	
	/**
	* Finds all educations linked with the given user's id
	* @param userId Id of the user 
	* @return List of all educations found to be linked to the id
	*/
	public List<Education> getUserEducations(int userId) {
		List<Education> retrievedEducations = educationRepo.findAllByPortfolioUserId(userId);
	
		return retrievedEducations;
	}
	
	/**
	* Finds all educations linked with the given portfolio id
	* @param portfolioId the id of a portfolio
	* @return List of all educations associated with the portfolio
	*/
	public List<Education> getPortfolioEducations(int portfolioId) {
		List<Education> retrievedEducations = educationRepo.findAllByPortfolioId(portfolioId);
	
		return retrievedEducations;
	}
	
}

