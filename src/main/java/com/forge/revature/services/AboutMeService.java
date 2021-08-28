package com.forge.revature.services;

import java.util.List;
import java.util.Optional;
import com.forge.revature.models.AboutMe;
import com.forge.revature.repo.AboutMeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Max Lee
 * @version 1.0
 * 
 * Service for AboutMe. Has CRUD functionality described per method
 * Code moved to Service Layer from Controller Layer by Aaron Killeen
 */
@Service
@AllArgsConstructor
public class AboutMeService {

    AboutMeRepo aboutMeRepo;

    /**
     * Retrieves all about mes stored in the database
     * @return List of all about mes in the database in JSON format
     */
    public List<AboutMe> getAll() {
        List<AboutMe> aboutMes = aboutMeRepo.findAll();
        return aboutMes;
    }

    /**
     * Creates a new about me in the database
     * @param aboutMe new about me being created
     * @return the representation of the about me with its newly generated primary key.
     */
    public AboutMe postAboutMe(AboutMe aboutMe) {
        return aboutMeRepo.save(aboutMe);
    }

    /**
     * Retrieves an about me based on the given ID
     * @param id id of the about me
     * @return Single about me found
     */
    public AboutMe getAboutMe(int id) {
        return aboutMeRepo.findById(id).get();
    }

    /**
     * Updates the about me associated with the id
     * @param newaboutMe updated information
     * @param aboutMeId ID of the about me being updated
     */
    public void updateAboutMe(AboutMe newAboutMe, int aboutMeId) {
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
    public void deleteAboutMe(int aboutMeId) {
        aboutMeRepo.deleteById(aboutMeId);
    }

    /**
     * Gets the about me based on the user's id
     * @param userId id of the user
     * @return about me found to be linked to the user
     */
    public AboutMe getUserAboutMe(int userId) {
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
    public AboutMe getPortfolioAboutMe(int portfolioId) {
        Optional<AboutMe> retrievedAboutMe = aboutMeRepo.findByPortfolioId(portfolioId);

        if (retrievedAboutMe.isPresent()) {
            return retrievedAboutMe.get();
        }
        return null;
    }
}
