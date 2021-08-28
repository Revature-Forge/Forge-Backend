package com.forge.revature.controllers;

import java.util.List;
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
import com.forge.revature.services.WorkHistoryService;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/workhistory")
@AllArgsConstructor
public class WorkHistoryController {

	WorkHistoryService workHistoryService;

  @GetMapping
  public List<WorkHistory> getAll() {
    return workHistoryService.getAll();
  }

  @GetMapping("/{id}")
  public WorkHistory getWorkHistory(@PathVariable int id) {
	  return workHistoryService.getWorkHistory(id);
  }

  @PostMapping
  public WorkHistory postWorkHistory(@RequestBody WorkHistory workHistory) {
	  return workHistoryService.postWorkHistory(workHistory);
  }

  @PutMapping
  public WorkHistory updateWorkHistory(@RequestBody WorkHistory updateWorkHist) {
	  return workHistoryService.updateWorkHistory(updateWorkHist);
  }

  @DeleteMapping("/{id}")
  public void deleteWorkHistory(@PathVariable int id) {
	  workHistoryService.deleteWorkHistory(id);
  }

  @GetMapping("/portfolio/{id}")
  public List<WorkHistory> getByPortfolioId(@PathVariable int id) {
	  return workHistoryService.getByPortfolioId(id);
  }

  @GetMapping("/portfolio/all/{id}")
  public List<WorkHistory> getPortfolioWorkHistories(@PathVariable(name = "id") int portfolioId) {
	  return workHistoryService.getPortfolioWorkHistories(portfolioId);
  }
  
}