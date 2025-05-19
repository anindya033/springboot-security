package com.crafter.security.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crafter.security.entity.UserEntity;
import com.crafter.security.repo.UserRepository;
import com.crafter.security.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private final UserRepository userRepo;
	
	private final UserService userService;
	
	public UserController(UserRepository repo, UserService userService) {
		this.userRepo = repo;
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public UserEntity register (@RequestBody UserEntity user) {
		//return userRepo.save(user);
		return userService.register(user);
	}
	
	@PostMapping("/login")
    public String login(@RequestBody UserEntity user) {
		
		return userService.verify(user);
    }

}
