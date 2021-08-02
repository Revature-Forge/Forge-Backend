package com.forge.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forge.revature.models.WorkExperience;
import com.forge.revature.repo.WorkExperienceRepo;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/workexperience")
public class WorkExperienceController {
    @Autowired
    WorkExperienceRepo repo;

    public WorkExperienceController() {
    }

    public WorkExperienceController(WorkExperienceRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<WorkExperience>> allExperience() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<WorkExperience>> getExperience(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(repo.findById(id), HttpStatus.OK);
    }

    @GetMapping("/portfolio/{id}")
    public ResponseEntity<List<WorkExperience>> getPortfolio(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(repo.findByPortfolio_Id(id), HttpStatus.OK);
    }

    @PostMapping
    public void createExperience(@RequestBody WorkExperience work) {
        repo.save(work);
    }

    @PostMapping("/{id}")
    public void updateExperience(@PathVariable(name = "id") long id, @RequestBody WorkExperience work) {
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

    @DeleteMapping(value = "/{id}")
    public void deleteExperience(@PathVariable(name = "id") long id) {
        repo.deleteById(id);
    }

    @GetMapping("/portfolio/all/{id}")
    public List<WorkExperience> getPortfolioWorkExperiences(@PathVariable(name = "id") int portfolioId) {
        List<WorkExperience> retrievedWorkExperiences = repo.findAllByPortfolioId(portfolioId);

        return retrievedWorkExperiences;
    }
}
