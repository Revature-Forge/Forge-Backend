package com.forge.revature.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.forge.revature.models.GitHub;
import com.forge.revature.models.Portfolio;

@Repository
public interface GitHubRepo extends JpaRepository<GitHub, Integer>{
  List<GitHub> findByPortfolio(Portfolio portfolio);
  List<GitHub> findAllByPortfolioId(int id);
  
  Optional<GitHub> deleteByPortfolioId(Integer portfolioID);
}