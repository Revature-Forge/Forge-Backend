package com.forge.revature.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.forge.revature.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
    public Optional<User> findByEmail(String email);
    
    public List<User> findAllByAdmin(boolean admin);
}
