package com.forge.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.forge.revature.models.WorkHistory;
import com.forge.revature.repo.WorkHistoryRepo;
import com.forge.revature.models.Portfolio;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.exception.NotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/workhistory")
public class WorkHistoryController {
  @Autowired
  private WorkHistoryRepo workHistoryRepo;

  @Autowired
  private PortfolioRepo portfolioRepo;

  @GetMapping
  public List<WorkHistory> getAll() {
    List<WorkHistory> workHistory = workHistoryRepo.findAll();
    return workHistory;
  }

  @GetMapping("/{id}")
  public WorkHistory getWorkHistory(@PathVariable int id) {
    return workHistoryRepo.findById(id).orElseThrow(() -> new NotFoundException("WorkHistory not Found for ID: " + id));
  }

  @PostMapping
  public WorkHistory postWorkHistory(@RequestBody WorkHistory workHistory) {
    return workHistoryRepo.save(workHistory);
  }

  @PutMapping
  public WorkHistory updateWorkHistory(@RequestBody WorkHistory updateWorkHist) {
    WorkHistory prevWorkHist = workHistoryRepo.findById(updateWorkHist.getId())
        .orElseThrow(() -> new NotFoundException("WorkHistory not Found for ID: " + updateWorkHist.getId()));

    prevWorkHist.setTitle(updateWorkHist.getTitle());
    prevWorkHist.setEmployer(updateWorkHist.getEmployer());
    prevWorkHist.setResponsibilities(updateWorkHist.getResponsibilities());
    prevWorkHist.setDescription(updateWorkHist.getDescription());
    prevWorkHist.setTools(updateWorkHist.getTools());
    prevWorkHist.setStartDate(updateWorkHist.getStartDate());
    prevWorkHist.setEndDate(updateWorkHist.getEndDate());

    return workHistoryRepo.save(prevWorkHist);
  }

  @DeleteMapping("/{id}")
  public void deleteWorkHistory(@PathVariable int id) {
    WorkHistory exist = workHistoryRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("WorkHistory not Found for ID: " + id));
    workHistoryRepo.deleteById(exist.getId());
  }

  @GetMapping("/portfolio/{id}")
  public List<WorkHistory> getByPortfolioId(@PathVariable int id) {
    Portfolio portfolio = portfolioRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("Portfolio not Found for ID: " + id));
    return workHistoryRepo.findByPortfolio(portfolio);
  }

  @GetMapping("/portfolio/all/{id}")
  public List<WorkHistory> getPortfolioWorkHistories(@PathVariable(name = "id") int portfolioId) {
    List<WorkHistory> retrievedWorkHistories = workHistoryRepo.findAllByPortfolioId(portfolioId);

    return retrievedWorkHistories;
  }
}