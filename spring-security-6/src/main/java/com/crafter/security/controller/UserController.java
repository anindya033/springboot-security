package com.crafter.security.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crafter.security.entity.UserEntity;
import com.crafter.security.repo.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private final UserRepository userRepo;
	
	public UserController(UserRepository repo) {
		this.userRepo = repo;
	}
	
	@PostMapping("/register")
	public UserEntity register (@RequestBody UserEntity user) {
		return userRepo.save(user);
	}
	
	@PostMapping("/login")
    public String login(@RequestBody UserEntity user) {
		UserEntity userObj = userRepo.findByUsername(user.getUsername());
		if (!Objects.isNull(userObj)) {
            
            return "Success" ;
        }
       /*
		Optional<UserEntity> userOpt = userRepo.findByUsername(user.getUsername());

        if (userOpt.isPresent()) {
            UserEntity user1 = userOpt.get();
            // In a real app, use BCrypt password encoder to compare hashes
            return "Success" ;//user.getPassword().equals(password);
        }
        */
        return "Username not found";
    }

}
