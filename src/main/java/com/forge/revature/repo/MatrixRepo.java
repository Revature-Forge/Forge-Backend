package com.forge.revature.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forge.revature.models.Matrix;
import com.forge.revature.models.Portfolio;

@Repository
public interface MatrixRepo extends JpaRepository<Matrix, Integer> {
	public List<Matrix> findAllByPortfolio(Portfolio port);
	List<Matrix> findAllByPortfolioId(int id);
	
	Optional<Matrix> deleteByPortfolioId(Integer portfolioID);
}
