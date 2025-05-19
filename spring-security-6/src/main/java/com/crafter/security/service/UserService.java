package com.crafter.security.service;

import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crafter.security.entity.UserEntity;
import com.crafter.security.repo.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepo;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final AuthenticationManager authenticationManager;
	
	private final JWTService jwtService;

	public UserService(UserRepository userRepo, BCryptPasswordEncoder bCryptPasswordEncoder,
			AuthenticationManager authenticationManager, JWTService jwtService) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	public UserEntity register(UserEntity user) {
		// TODO Auto-generated method stub
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	public String verify(UserEntity user) {

		Authentication authManager = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		UserEntity userObj = userRepo.findByUsername(user.getUsername());

		if (authManager.isAuthenticated()) {
			return jwtService.generateToken(user);
		}

		return "Username not found";
	}

}
