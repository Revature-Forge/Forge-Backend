package com.forge.revature.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.forge.revature.models.Equivalency;
import com.forge.revature.repo.EquivalencyRepo;

import org.springframework.beans.factory.annotation.Autowired;
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
public class EquivalencyController {
    @Autowired
    EquivalencyRepo eqRepo;

    public EquivalencyController(EquivalencyRepo eqRepo) {
        this.eqRepo = eqRepo;
    }

    public EquivalencyController() {
    }

    @GetMapping
    public List<Equivalency> getAll(){
        List<Equivalency> eqs = StreamSupport.stream(eqRepo.findAll().spliterator() , false)
            .collect(Collectors.toList());
        return eqs;
    }

    @GetMapping("/{id}")
    public Equivalency getByID(@PathVariable (name = "id") int id){
        return eqRepo.findById(id).get();
    }

    @GetMapping("/portfolios/all/{id}")
    public List<Equivalency> getAllEquivalenciesByPortfolioID(@PathVariable int id){
        List<Equivalency> eqivs = eqRepo.findAllByPortfolioId(id);
        return eqivs;
    }

    @PostMapping
    public Equivalency postEquiv(@RequestBody Equivalency equiv){
        return eqRepo.save(equiv);
    }
    @PostMapping("/{id}")
    public void updateEquivalency(@PathVariable int id , @RequestBody Equivalency updated){
        Optional<Equivalency> old = eqRepo.findById(id);

        if(old.isPresent()){
            old.get().setHeader(updated.getHeader());
            old.get().setPortfolio(updated.getPortfolio());
            old.get().setValue(updated.getValue());
        }
        eqRepo.save(old.get());

    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteEquiv(@PathVariable int id) throws ResourceNotFoundException{
        Optional<Equivalency> equiv = eqRepo.findById(id);

        if(equiv.isPresent()){
            eqRepo.delete(equiv.get());
        }else{
            throw new ResourceNotFoundException("The Equivalency to be deleted could not be found");
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }



}
