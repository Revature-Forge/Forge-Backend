package com.forge.revature.controllers;

import java.util.List;
import com.forge.revature.models.Education;
import com.forge.revature.services.EducationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Max Lee
 * @version 1.0
 * 
 * Controller for Education. Has CRUD functionality described per method
 * Code moved to Service Layer from Controller Layer by Aaron Killeen
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/education")
@AllArgsConstructor
public class EducationController {
	
	private EducationService educationService;
    /**
     * Retrieves all educations stored in the database
     * @return List of all Educations in the database in JSON format
     */
    @GetMapping
    public List<Education> getAll() {
    	return educationService.getAll();
    }

    /**
     * Retrieves an education based on the given ID
     * @param id id of the education
     * @return Single education found
     */
    @GetMapping("/{id}")
    public Education getEducation(@PathVariable int id) {
        return educationService.getEducation(id);
    }

    /**
     * Creates a new education in the database
     * @param education new education being created
     * @return the representation of the education with its newly generated primary key.
     */
    @PostMapping
    public Education postEducation(@RequestBody Education education) {
    	return educationService.postEducation(education);
    }

    /**
     * Updates the education associated with the id
     * @param newEducation updated information
     * @param educationId ID of the education being updated
     */
    @PostMapping("/{id}")
    public void updateEducation(@RequestBody Education newEducation, @PathVariable(name = "id") int educationId) {
    	educationService.updateEducation(newEducation, educationId);
    }

    /**
     * Deletes the associated education
     * @param educationId ID of the education being deleted
     */
    @DeleteMapping("/{id}")
    public void deleteEducation(@PathVariable(name = "id") int educationId) {
        educationService.deleteEducation(educationId);
    }

    /**
     * Gets the education based on the user's id
     * @param userId id of the user
     * @return education found to be linked to the user
     */
    @GetMapping("/user/{id}")
    public Education getUserEducation(@PathVariable(name = "id") int userId) {
    	return educationService.getUserEducation(userId);
    }

    /**
     * Finds the education based on the portfolio id
     * @param portfolioId ID of the portfolio
     * @return the education found to be linked to the portfolio id
     */
    @GetMapping("/portfolio/{id}")
    public Education getPortfolioEducation(@PathVariable(name = "id") int portfolioId) {
    	return educationService.getPortfolioEducation(portfolioId);
    }

    /**
     * Finds all educations linked with the given user's id
     * @param userId Id of the user 
     * @return List of all educations found to be linked to the id
     */
    @GetMapping("/user/all/{id}")
    public List<Education> getUserEducations(@PathVariable(name = "id") int userId) {
    	return educationService.getUserEducations(userId);
    }

    /**
     * Finds all educations linked with the given portfolio id
     * @param portfolioId the id of a portfolio
     * @return List of all educations associated with the portfolio
     */
    @GetMapping("/portfolio/all/{id}")
    public List<Education> getPortfolioEducations(@PathVariable(name = "id") int portfolioId) {
    	return educationService.getPortfolioEducations(portfolioId);
    }
}
