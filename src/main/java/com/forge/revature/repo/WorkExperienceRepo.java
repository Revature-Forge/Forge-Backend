package com.forge.revature.repo;

import java.util.List;
import java.util.Optional;

import com.forge.revature.models.WorkExperience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepo extends JpaRepository<WorkExperience, Long>{
    List<WorkExperience> findByPortfolio_Id(int id);
    List<WorkExperience> findAllByPortfolioId(int id);
    
    Optional<WorkExperience> deleteByPortfolioId(Integer portfolioID);
}
