package com.forge.revature.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.forge.revature.models.User;
import com.forge.revature.services.UserService;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {
	
	private UserService userService;
	
	@GetMapping
	public List<User> getAll() {
		return userService.getAll();
	}
	
	@GetMapping("/{id}")
	public User getByID(@PathVariable(name = "id") int id){
		return userService.getByID(id);
	}
	
	@PostMapping("/login")
	public User login(@RequestHeader(name = "email") String email , @RequestHeader(name = "password") String password){
		return userService.login(email, password);
	}
	@PostMapping
	public User postUser(@RequestBody User user){
		return userService.postUser(user);
	}
	
	@PostMapping("user/{id}")
	public void updateUser(@PathVariable int id , @RequestBody User newU){
		userService.updateUser(id, newU);
	}
	
	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteEquiv(@PathVariable int id) throws ResourceNotFoundException{
		return userService.deleteEquiv(id);
	}

}
