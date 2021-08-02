package com.forge.revature.repo;

import java.util.List;
import java.util.Optional;

import com.forge.revature.models.Education;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Max Lee
 * @version 1.0
 * 
 * Repository designed for Education model. Extra methods are to find an education or all educations linked to a portfolio using its portfolio foreign key.
 */
public interface EducationRepo extends JpaRepository<Education, Integer>{
    Optional<Education> findByPortfolioId(Integer portfolioId);
    Optional<Education> findByPortfolioUserId(Integer userId);
    Optional<Education> deleteByPortfolioId(Integer portfolioID);

    List<Education> findAllByPortfolioId(Integer portfolioId);
    List<Education> findAllByPortfolioUserId(Integer userId);
}