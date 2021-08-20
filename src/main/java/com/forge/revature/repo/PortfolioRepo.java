package com.forge.revature.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.forge.revature.models.Portfolio;


@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio, Integer>{
    List<Portfolio> findAllByUserId(Integer userId);
    
    @Query("SELECT COUNT(APPROVED) FROM USERS WHERE APPROVED = true GROUP BY admin_id ORDER BY admin_id")
    List<Integer> getApproveCount();
    
    @Query("SELECT COUNT(APPROVED) FROM USERS WHERE APPROVED = false AND REVIEWED = true GROUP BY admin_id ORDER BY admin_id")
    List<Integer> getDeniedCount();
}
