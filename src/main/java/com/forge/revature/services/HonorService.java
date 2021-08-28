package com.forge.revature.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.forge.revature.models.Honor;
import com.forge.revature.repo.HonorRepo;
import com.forge.revature.models.Portfolio;
import com.forge.revature.repo.PortfolioRepo;
import lombok.AllArgsConstructor;
import com.forge.revature.exception.NotFoundException;

@Service
@AllArgsConstructor
public class HonorService{
	
	private HonorRepo honorRepo;
	private PortfolioRepo portfolioRepo;
	
	public List<Honor> getAll() {
		List<Honor> honors = honorRepo.findAll();
		return honors;
	}
	
	public Honor getHonor(int id) {
		return honorRepo.findById(id).orElseThrow(() -> new NotFoundException("Honor not Found for ID: " + id));
	}
	
	public Honor postHonor(Honor honors) {
		return honorRepo.save(honors);
	}
	
	public Honor updateHonor(Honor updateHonor) {
		Honor prevHonors = honorRepo.findById(updateHonor.getId())
			.orElseThrow(() -> new NotFoundException("Honor not Found for ID: " + updateHonor.getId()));
	
		prevHonors.setTitle(updateHonor.getTitle());
		prevHonors.setDescription(updateHonor.getDescription());
		prevHonors.setDateReceived(updateHonor.getDateReceived());
		prevHonors.setReceivedFrom(updateHonor.getReceivedFrom());
		
		return honorRepo.save(prevHonors);
	}
	
	public void deleteHonor(int id) {
		Honor exist = honorRepo.findById(id).orElseThrow(() -> new NotFoundException("Honor not Found for ID: " + id));
		honorRepo.deleteById(exist.getId());
	}
	
	public List<Honor> getByPortfolioId(int id) {
		Portfolio portfolio = portfolioRepo.findById(id)
			.orElseThrow(() -> new NotFoundException("Portfolio not Found for ID: " + id));
		return honorRepo.findByPortfolio(portfolio);
	}
	
	public List<Honor> getPortfolioHonors(int portfolioId) {
		List<Honor> retrievedHonors = honorRepo.findAllByPortfolioId(portfolioId);
	
		return retrievedHonors;
	}

}
