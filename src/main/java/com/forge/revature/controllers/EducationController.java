package com.forge.revature.controllers;

import java.util.List;
import java.util.Optional;

import com.forge.revature.models.Education;
import com.forge.revature.repo.EducationRepo;

import org.springframework.beans.factory.annotation.Autowired;
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
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/education")
public class EducationController {
    @Autowired
    EducationRepo educationRepo;

    public EducationController() {
    }

    public EducationController(EducationRepo educationRepo) {
        this.educationRepo = educationRepo;
    }

    /**
     * Retrieves all educations stored in the database
     * @return List of all Educations in the database in JSON format
     */
    @GetMapping
    public List<Education> getAll() {
        List<Education> educations = educationRepo.findAll();
        return educations;
    }

    /**
     * Retrieves an education based on the given ID
     * @param id id of the education
     * @return Single education found
     */
    @GetMapping("/{id}")
    public Education getEducation(@PathVariable int id) {
        return educationRepo.findById(id).get();
    }

    /**
     * Creates a new education in the database
     * @param education new education being created
     * @return the representation of the education with its newly generated primary key.
     */
    @PostMapping
    public Education postEducation(@RequestBody Education education) {
        return educationRepo.save(education);
    }

    /**
     * Updates the education associated with the id
     * @param newEducation updated information
     * @param educationId ID of the education being updated
     */
    @PostMapping("/{id}")
    public void updateEducation(@RequestBody Education newEducation, @PathVariable(name = "id") int educationId) {
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
    @DeleteMapping("/{id}")
    public void deleteEducation(@PathVariable(name = "id") int educationId) {
        educationRepo.deleteById(educationId);
    }

    /**
     * Gets the education based on the user's id
     * @param userId id of the user
     * @return education found to be linked to the user
     */
    @GetMapping("/user/{id}")
    public Education getUserEducation(@PathVariable(name = "id") int userId) {
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
    @GetMapping("/portfolio/{id}")
    public Education getPortfolioEducation(@PathVariable(name = "id") int portfolioId) {
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
    @GetMapping("/user/all/{id}")
    public List<Education> getUserEducations(@PathVariable(name = "id") int userId) {
        List<Education> retrievedEducations = educationRepo.findAllByPortfolioUserId(userId);

        return retrievedEducations;
    }

    /**
     * Finds all educations linked with the given portfolio id
     * @param portfolioId the id of a portfolio
     * @return List of all educations associated with the portfolio
     */
    @GetMapping("/portfolio/all/{id}")
    public List<Education> getPortfolioEducations(@PathVariable(name = "id") int portfolioId) {
        List<Education> retrievedEducations = educationRepo.findAllByPortfolioId(portfolioId);

        return retrievedEducations;
    }
}
