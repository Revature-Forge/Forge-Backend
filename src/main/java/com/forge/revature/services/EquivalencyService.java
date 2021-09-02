package com.forge.revature.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.forge.revature.models.Equivalency;
import com.forge.revature.repo.EquivalencyRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EquivalencyService {
	
	EquivalencyRepo eqRepo;
	
	public List<Equivalency> getAll(){
		List<Equivalency> eqs = StreamSupport.stream(eqRepo.findAll().spliterator() , false)
			.collect(Collectors.toList());
		return eqs;
	}
	
	public Equivalency getByID(int id){
		return eqRepo.findById(id).get();
	}
	
	public List<Equivalency> getAllEquivalenciesByPortfolioID(int id){
		List<Equivalency> eqivs = eqRepo.findAllByPortfolioId(id);
		return eqivs;
	}
	
	public Equivalency postEquiv(Equivalency equiv){
		return eqRepo.save(equiv);
	}
	
	public void updateEquivalency(int id , Equivalency updated){
		Optional<Equivalency> old = eqRepo.findById(id);
	
		if(old.isPresent()){
			old.get().setHeader(updated.getHeader());
			old.get().setPortfolio(updated.getPortfolio());
			old.get().setValue(updated.getValue());
		}
		eqRepo.save(old.get());
	
	}
	
	public Map<String, Boolean> deleteEquiv(int id) throws ResourceNotFoundException{
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
