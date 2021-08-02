package com.forge.revature.repo;

import java.util.List;
import java.util.Optional;

import com.forge.revature.models.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long>{
    List<Project> findByPortfolio_Id(int id);
    List<Project> findAllByPortfolioId(int id);
    
    Optional<Project> deleteByPortfolioId(Integer portfolioID);
}