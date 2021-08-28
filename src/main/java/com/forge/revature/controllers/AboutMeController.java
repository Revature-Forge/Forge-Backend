package com.forge.revature.controllers;

import java.util.List;
import com.forge.revature.models.AboutMe;
import com.forge.revature.services.AboutMeService;
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
 * Controller for AboutMe. Has CRUD functionality described per method
 * Code moved to Service Layer from Controller Layer by Aaron Killeen
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/aboutMe")
@AllArgsConstructor
public class AboutMeController {
	
	private AboutMeService aboutMeService;
	/**
	* Retrieves all about mes stored in the database
	* @return List of all about mes in the database in JSON format
	*/
	@GetMapping
	public List<AboutMe> getAll() {
		return aboutMeService.getAll();
	}
	
	/**
	* Creates a new about me in the database
	* @param aboutMe new about me being created
	* @return the representation of the about me with its newly generated primary key.
	*/
	@PostMapping
	public AboutMe postAboutMe(@RequestBody AboutMe aboutMe) {
		return aboutMeService.postAboutMe(aboutMe);
	}
	
	/**
	* Retrieves an about me based on the given ID
	* @param id id of the about me
	* @return Single about me found
	*/
	@GetMapping("/{id}")
	public AboutMe getAboutMe(@PathVariable(name = "id") int id) {
		return aboutMeService.getAboutMe(id);
	}
	
	/**
	* Updates the about me associated with the id
	* @param newaboutMe updated information
	* @param aboutMeId ID of the about me being updated
	*/
	@PostMapping("/{id}")
	public void updateAboutMe(@RequestBody AboutMe newAboutMe, @PathVariable(name = "id") int aboutMeId) {
		aboutMeService.updateAboutMe(newAboutMe, aboutMeId);
	}
	
	/**
	* Deletes the associated about me
	* @param aboutMeId ID of the about me being deleted
	*/
	@DeleteMapping("/{id}")
	public void deleteAboutMe(@PathVariable(name = "id") int aboutMeId) {
		aboutMeService.deleteAboutMe(aboutMeId);
	}
	
	/**
	* Gets the about me based on the user's id
	* @param userId id of the user
	* @return about me found to be linked to the user
	*/
	@GetMapping("/user/{id}")
	public AboutMe getUserAboutMe(@PathVariable(name = "id") int userId) {
		return aboutMeService.getUserAboutMe(userId);
	}
	
	/**
	* Finds the about me based on the portfolio id
	* @param portfolioId ID of the portfolio
	* @return the about me found to be linked to the portfolio id
	*/
	@GetMapping("/portfolio/{id}")
	public AboutMe getPortfolioAboutMe(@PathVariable(name = "id") int portfolioId) {
		return aboutMeService.getPortfolioAboutMe(portfolioId);
	}
}
