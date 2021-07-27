package com.forge.revature.controllers;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.forge.revature.models.User;
import com.forge.revature.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserController() {
    }

    @GetMapping
    public List<User> getAll() {
        List<User> users = StreamSupport.stream(userRepo.findAll().spliterator(), false)
            .collect(Collectors.toList());
        return users;
    }

    @GetMapping("/{id}")
    public User getByID(@PathVariable(name = "id") int id){
        return userRepo.findById(id).get();
    }

    @PostMapping("/login")
    public User login(@RequestHeader(name = "email") String email , @RequestHeader(name = "password") String password){
        Optional<User> user = userRepo.findByEmail(email);
        if(user.isPresent()){
            if(password.equals(user.get().getPassword())){
                return user.get();
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Either Email or Password is incorrect");
        
    }
    @PostMapping
    public User postUser(@RequestBody User user){
        return userRepo.save(user);
    }

    @PostMapping("user/{id}")
    public void updateUser(@PathVariable int id , @RequestBody User newU){
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

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteEquiv(@PathVariable int id) throws ResourceNotFoundException{
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
