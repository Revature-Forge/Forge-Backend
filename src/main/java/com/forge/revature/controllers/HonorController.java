package com.forge.revature.controllers;

import java.util.List;
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
import com.forge.revature.services.HonorService;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/honor")
@AllArgsConstructor
public class HonorController {

	private HonorService honorService;

  @GetMapping
  public List<Honor> getAll() {
	  return honorService.getAll();
  }

  @GetMapping("/{id}")
  public Honor getHonor(@PathVariable int id) {
	  return honorService.getHonor(id);
  }

  @PostMapping
  public Honor postHonor(@RequestBody Honor honors) {
	  return honorService.postHonor(honors);
  }

  @PutMapping
  public Honor updateHonor(@RequestBody Honor updateHonor) {
	  return honorService.updateHonor(updateHonor);
  }

  @DeleteMapping("/{id}")
  public void deleteHonor(@PathVariable int id) {
	  honorService.deleteHonor(id);
  }

  @GetMapping("/portfolio/{id}")
  public List<Honor> getByPortfolioId(@PathVariable int id) {
	  return honorService.getByPortfolioId(id);
  }

  @GetMapping("/portfolio/all/{id}")
  public List<Honor> getPortfolioHonors(@PathVariable(name = "id") int portfolioId) {
	  return honorService.getPortfolioHonors(portfolioId);
  }

}