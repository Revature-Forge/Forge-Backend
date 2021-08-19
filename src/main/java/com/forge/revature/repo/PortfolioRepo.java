package com.forge.revature.repo;

import java.util.List;

import com.forge.revature.models.Portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio, Integer>{
    List<Portfolio> findAllByUserId(Integer userId);
    
    
}
