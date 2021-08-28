package com.forge.revature.controllers;

import java.util.List;
import java.util.Map;
import com.forge.revature.models.Equivalency;
import com.forge.revature.services.EquivalencyService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/equiv")
@AllArgsConstructor
public class EquivalencyController {
    
	private EquivalencyService equivalencyService;
	
    @GetMapping
    public List<Equivalency> getAll(){
        return equivalencyService.getAll();
    }

    @GetMapping("/{id}")
    public Equivalency getByID(@PathVariable (name = "id") int id){
    	return equivalencyService.getByID(id);
    }

    @GetMapping("/portfolios/all/{id}")
    public List<Equivalency> getAllEquivalenciesByPortfolioID(@PathVariable int id){
    	return equivalencyService.getAllEquivalenciesByPortfolioID(id);
    }

    @PostMapping
    public Equivalency postEquiv(@RequestBody Equivalency equiv){
    	return equivalencyService.postEquiv(equiv);
    }
    @PostMapping("/{id}")
    public void updateEquivalency(@PathVariable int id , @RequestBody Equivalency updated){
    	equivalencyService.updateEquivalency(id, updated);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteEquiv(@PathVariable int id) throws ResourceNotFoundException{
    	return equivalencyService.deleteEquiv(id);
    }
    
}
