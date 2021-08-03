package com.forge.revature.repo;

import java.util.Optional;
import com.forge.revature.models.AboutMe;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Max Lee
 * @version 1.0
 * 
 * Repository designed for AboutMe model. Extra methods are to find an about me using its portfolio foreign key.
 */
public interface AboutMeRepo extends JpaRepository<AboutMe, Integer>{
    Optional<AboutMe> findByPortfolioId(Integer portfolioId);
    Optional<AboutMe> findByPortfolioUserId(Integer userId);
    
    Optional<AboutMe> deleteByPortfolioId(Integer portfolioID);
}
