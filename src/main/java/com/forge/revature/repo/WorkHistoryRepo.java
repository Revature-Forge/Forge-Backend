package com.forge.revature.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forge.revature.models.WorkHistory;
import com.forge.revature.models.Portfolio;

@Repository
public interface WorkHistoryRepo extends JpaRepository<WorkHistory, Integer>{
  List<WorkHistory> findByPortfolio(Portfolio portfolio);
  List<WorkHistory> findAllByPortfolioId(int id);
  
  Optional<WorkHistory> deleteByPortfolioId(Integer portfolioID);
}