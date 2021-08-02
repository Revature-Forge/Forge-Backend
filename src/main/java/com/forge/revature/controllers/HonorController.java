package com.forge.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.forge.revature.models.Honor;
import com.forge.revature.repo.HonorRepo;
import com.forge.revature.models.Portfolio;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.exception.NotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/honor")
public class HonorController {
  @Autowired
  private HonorRepo honorRepo;

  @Autowired
  private PortfolioRepo portfolioRepo;

  @GetMapping
  public List<Honor> getAll() {
    List<Honor> honors = honorRepo.findAll();
    return honors;
  }

  @GetMapping("/{id}")
  public Honor getHonor(@PathVariable int id) {
    return honorRepo.findById(id).orElseThrow(() -> new NotFoundException("Honor not Found for ID: " + id));
  }

  @PostMapping
  public Honor postHonor(@RequestBody Honor honors) {
    return honorRepo.save(honors);
  }

  @PutMapping
  public Honor updateHonor(@RequestBody Honor updateHonor) {
    Honor prevHonors = honorRepo.findById(updateHonor.getId())
      .orElseThrow(() -> new NotFoundException("Honor not Found for ID: " + updateHonor.getId()));

    prevHonors.setTitle(updateHonor.getTitle());
    prevHonors.setDescription(updateHonor.getDescription());
    prevHonors.setDateReceived(updateHonor.getDateReceived());
    prevHonors.setReceivedFrom(updateHonor.getReceivedFrom());

    return honorRepo.save(prevHonors);
  }

  @DeleteMapping("/{id}")
  public void deleteHonor(@PathVariable int id) {
    Honor exist = honorRepo.findById(id).orElseThrow(() -> new NotFoundException("Honor not Found for ID: " + id));
    honorRepo.deleteById(exist.getId());
  }

  @GetMapping("/portfolio/{id}")
  public List<Honor> getByPortfolioId(@PathVariable int id) {
    Portfolio portfolio = portfolioRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("Portfolio not Found for ID: " + id));
    return honorRepo.findByPortfolio(portfolio);
  }

  @GetMapping("/portfolio/all/{id}")
  public List<Honor> getPortfolioHonors(@PathVariable(name = "id") int portfolioId) {
    List<Honor> retrievedHonors = honorRepo.findAllByPortfolioId(portfolioId);

    return retrievedHonors;
  }

}