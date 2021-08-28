package com.forge.revature.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.forge.revature.models.WorkHistory;
import com.forge.revature.repo.WorkHistoryRepo;
import lombok.AllArgsConstructor;
import com.forge.revature.models.Portfolio;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.exception.NotFoundException;

@Service
@AllArgsConstructor
public class WorkHistoryService{

  private WorkHistoryRepo workHistoryRepo;
  private PortfolioRepo portfolioRepo;

  public List<WorkHistory> getAll() {
    List<WorkHistory> workHistory = workHistoryRepo.findAll();
    return workHistory;
  }

  public WorkHistory getWorkHistory(int id) {
    return workHistoryRepo.findById(id).orElseThrow(() -> new NotFoundException("WorkHistory not Found for ID: " + id));
  }

  public WorkHistory postWorkHistory(WorkHistory workHistory) {
    return workHistoryRepo.save(workHistory);
  }

  public WorkHistory updateWorkHistory(WorkHistory updateWorkHist) {
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

  public void deleteWorkHistory(int id) {
    WorkHistory exist = workHistoryRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("WorkHistory not Found for ID: " + id));
    workHistoryRepo.deleteById(exist.getId());
  }

  public List<WorkHistory> getByPortfolioId(int id) {
    Portfolio portfolio = portfolioRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("Portfolio not Found for ID: " + id));
    return workHistoryRepo.findByPortfolio(portfolio);
  }

  public List<WorkHistory> getPortfolioWorkHistories(int portfolioId) {
    List<WorkHistory> retrievedWorkHistories = workHistoryRepo.findAllByPortfolioId(portfolioId);

    return retrievedWorkHistories;
  }
}