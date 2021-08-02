package com.forge.revature.repo;

import java.util.List;
import java.util.Optional;

import com.forge.revature.models.Equivalency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquivalencyRepo extends JpaRepository<Equivalency, Integer>{
    public List<Equivalency> findAllByPortfolioId(int id);
    
    Optional<Equivalency> deleteByPortfolioId(Integer portfolioID);
}
