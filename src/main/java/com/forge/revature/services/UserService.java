package com.forge.revature.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.forge.revature.models.User;
import com.forge.revature.repo.UserRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	UserRepo userRepo;
	
	public List<User> getAll() {
		List<User> users = StreamSupport.stream(userRepo.findAll().spliterator(), false)
			.collect(Collectors.toList());
		return users;
	}
	
	public User getByID(int id){
		return userRepo.findById(id);
	}
	
	public User login(String email , String password){
		Optional<User> user = userRepo.findByEmail(email);
		if(user.isPresent()){
			if(password.equals(user.get().getPassword())){
				return user.get();
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Either Email or Password is incorrect");
		
	}
	
	public User postUser(User user){
		return userRepo.save(user);
	}
	
	public void updateUser(int id , User newU){
		Optional<User> old = userRepo.findById(id);
		if(old.isPresent()){
			old.get().setFName(newU.getFName());
			old.get().setAdmin(newU.isAdmin());
			old.get().setEmail(newU.getEmail());
			old.get().setLName(newU.getLName());
			old.get().setPassword(newU.getPassword());
			userRepo.save(old.get());
		}
	
	
	}
	
	public Map<String, Boolean> deleteEquiv(int id) throws ResourceNotFoundException{
		Optional<User> user = userRepo.findById(id);
	
		if(user.isPresent()){
			userRepo.delete(user.get());
		}else{
			throw new ResourceNotFoundException("The User to be deleted could not be found");
		}
	
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
