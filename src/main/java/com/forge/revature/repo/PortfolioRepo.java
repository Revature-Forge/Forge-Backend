package com.forge.revature.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.forge.revature.models.Portfolio;


@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio, Integer>{
    List<Portfolio> findAllByUserId(Integer userId);
    
    @Query(value = "SELECT COUNT(APPROVED) FROM portfolios WHERE APPROVED = true AND admin_id = ?1 GROUP BY admin_id", nativeQuery = true)
    Integer getApproveCount(int admin_id);
    
    @Query(value = "SELECT COUNT(APPROVED) FROM portfolios WHERE APPROVED = false AND REVIEWED = true AND admin_id = ?1 GROUP BY admin_id", nativeQuery = true)
    Integer getDeniedCount(int admin_id);
}