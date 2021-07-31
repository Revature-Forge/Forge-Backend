package com.forge.revature.controllers;

import java.util.List;
import java.util.Optional;

import com.forge.revature.models.AboutMe;
import com.forge.revature.repo.AboutMeRepo;

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
 * Controller for AboutMe. Has CRUD functionality described per method
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/aboutMe")
public class AboutMeController {
    @Autowired
    AboutMeRepo aboutMeRepo;

    public AboutMeController() {
    }

    public AboutMeController(AboutMeRepo repo) {
        this.aboutMeRepo = repo;
    }

    /**
     * Retrieves all about mes stored in the database
     * @return List of all about mes in the database in JSON format
     */
    @GetMapping
    public List<AboutMe> getAll() {
        List<AboutMe> aboutMes = aboutMeRepo.findAll();
        return aboutMes;
    }

    /**
     * Creates a new about me in the database
     * @param aboutMe new about me being created
     * @return the representation of the about me with its newly generated primary key.
     */
    @PostMapping
    public AboutMe postAboutMe(@RequestBody AboutMe aboutMe) {
        return aboutMeRepo.save(aboutMe);
    }

    /**
     * Retrieves an about me based on the given ID
     * @param id id of the about me
     * @return Single about me found
     */
    @GetMapping("/{id}")
    public AboutMe getAboutMe(@PathVariable(name = "id") int id) {
        return aboutMeRepo.findById(id).get();
    }

    /**
     * Updates the about me associated with the id
     * @param newaboutMe updated information
     * @param aboutMeId ID of the about me being updated
     */
    @PostMapping("/{id}")
    public void updateAboutMe(@RequestBody AboutMe newAboutMe, @PathVariable(name = "id") int aboutMeId) {
        Optional<AboutMe> oldAboutMe = aboutMeRepo.findById(aboutMeId);

        if (oldAboutMe.isPresent()) {

            oldAboutMe.get().setBio(newAboutMe.getBio());
            oldAboutMe.get().setEmail(newAboutMe.getEmail());
            oldAboutMe.get().setPhone(newAboutMe.getPhone());

            aboutMeRepo.save(oldAboutMe.get());
        }
    }

    /**
     * Deletes the associated about me
     * @param aboutMeId ID of the about me being deleted
     */
    @DeleteMapping("/{id}")
    public void deleteAboutMe(@PathVariable(name = "id") int aboutMeId) {
        aboutMeRepo.deleteById(aboutMeId);
    }

    /**
     * Gets the about me based on the user's id
     * @param userId id of the user
     * @return about me found to be linked to the user
     */
    @GetMapping("/user/{id}")
    public AboutMe getUserAboutMe(@PathVariable(name = "id") int userId) {
        Optional<AboutMe> retrievedAboutMe = aboutMeRepo.findByPortfolioUserId(userId);

        if (retrievedAboutMe.isPresent()) {
            return retrievedAboutMe.get();
        }
        return null;
    }

    /**
     * Finds the about me based on the portfolio id
     * @param portfolioId ID of the portfolio
     * @return the about me found to be linked to the portfolio id
     */
    @GetMapping("/portfolio/{id}")
    public AboutMe getPortfolioAboutMe(@PathVariable(name = "id") int portfolioId) {
        Optional<AboutMe> retrievedAboutMe = aboutMeRepo.findByPortfolioId(portfolioId);

        if (retrievedAboutMe.isPresent()) {
            return retrievedAboutMe.get();
        }
        return null;
    }
}
