package com.forge.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forge.revature.exception.NotFoundException;
import com.forge.revature.models.GitHub;
import com.forge.revature.models.Portfolio;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.GitHubRepo;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/github")
public class GitHubController {
  @Autowired
  private GitHubRepo gitRepo;
  
  @Autowired
  private PortfolioRepo portfolioRepo;

  @GetMapping
  public List<GitHub> getAll() {
    List<GitHub> git = gitRepo.findAll();
    return git;
  }

  @GetMapping("/{id}")
  public GitHub getGitHub(@PathVariable int id) {
    return gitRepo.findById(id).orElseThrow(() -> new NotFoundException("GitHub not Found for ID: " + id));
  }

  @PostMapping
  public GitHub postGitHub(@RequestBody GitHub gitHub) {
    return gitRepo.save(gitHub);
  }

  @PutMapping
  public GitHub updateGitHub(@RequestBody GitHub updateGit) {
    GitHub prevGit = gitRepo.findById(updateGit.getId())
      .orElseThrow(() -> new NotFoundException("GitHub not Found for ID: " + updateGit.getId()));

    prevGit.setUrl(updateGit.getUrl());
    prevGit.setImage(updateGit.getImage());

    return gitRepo.save(prevGit);
  }

  @DeleteMapping("/{id}")
  public void deleteGitHub(@PathVariable int id) {
    GitHub exist = gitRepo.findById(id).orElseThrow(() -> new NotFoundException("GitHub not Found for ID: " + id));
    gitRepo.deleteById(exist.getId());
  }

  @GetMapping("/portfolio/{id}")
  public List<GitHub> getByPortfolioId(@PathVariable int id) {
    Portfolio portfolio = portfolioRepo.findById(id)
      .orElseThrow(() -> new NotFoundException("Portfolio not Found for ID: " + id));
    return gitRepo.findByPortfolio(portfolio);
  }

  @GetMapping("/portfolio/all/{id}")
  public List<GitHub> getPortfolioGitHubs(@PathVariable(name = "id") int id) {
    List<GitHub> retrievedGitHubs = gitRepo.findAllByPortfolioId(id);

    return retrievedGitHubs;
  }
}