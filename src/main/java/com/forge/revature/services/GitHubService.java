package com.forge.revature.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.forge.revature.exception.NotFoundException;
import com.forge.revature.models.GitHub;
import com.forge.revature.models.Portfolio;
import com.forge.revature.repo.PortfolioRepo;
import lombok.AllArgsConstructor;
import com.forge.revature.repo.GitHubRepo;

@Service
@AllArgsConstructor
public class GitHubService {
	
	private GitHubRepo gitRepo;
	private PortfolioRepo portfolioRepo;
	
	public List<GitHub> getAll() {
		List<GitHub> git = gitRepo.findAll();
	return git;
	}
	
	public GitHub getGitHub(int id) {
		return gitRepo.findById(id).orElseThrow(() -> new NotFoundException("GitHub not Found for ID: " + id));
	}
	
	public GitHub postGitHub(GitHub gitHub) {
		return gitRepo.save(gitHub);
	}
	
	public GitHub updateGitHub(GitHub updateGit) {
		GitHub prevGit = gitRepo.findById(updateGit.getId())
			.orElseThrow(() -> new NotFoundException("GitHub not Found for ID: " + updateGit.getId()));
	
		prevGit.setUrl(updateGit.getUrl());
		prevGit.setImage(updateGit.getImage());
	
		return gitRepo.save(prevGit);
	}
	
	public void deleteGitHub(int id) {
		GitHub exist = gitRepo.findById(id).orElseThrow(() -> new NotFoundException("GitHub not Found for ID: " + id));
		gitRepo.deleteById(exist.getId());
	}
	
	public List<GitHub> getByPortfolioId(int id) {
		Portfolio portfolio = portfolioRepo.findById(id)
			.orElseThrow(() -> new NotFoundException("Portfolio not Found for ID: " + id));
		return gitRepo.findByPortfolio(portfolio);
	}
	
	public List<GitHub> getPortfolioGitHubs(int id) {
		List<GitHub> retrievedGitHubs = gitRepo.findAllByPortfolioId(id);
	
		return retrievedGitHubs;
	}
	
}
