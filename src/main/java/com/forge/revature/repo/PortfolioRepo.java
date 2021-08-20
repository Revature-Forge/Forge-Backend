package com.forge.revature.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.forge.revature.models.Portfolio;


@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio, Integer>{
    List<Portfolio> findAllByUserId(Integer userId);
    
    @Query("SELECT COUNT(approved) FROM Portfolio WHERE approved = true AND admin_id = ?1 GROUP BY admin_id")
    Integer getApproveCount(int admin_id);
    
    @Query("SELECT COUNT(approved) FROM Portfolio WHERE approved = false AND reviewed = true AND admin_id = ?1 GROUP BY admin_id")
    Integer getDeniedCount(int admin_id);
}