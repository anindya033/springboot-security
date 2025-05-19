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

	public UserService(UserRepository userRepo, BCryptPasswordEncoder bCryptPasswordEncoder,
			AuthenticationManager authenticationManager) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.authenticationManager = authenticationManager;
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
			return "12DFCMSDFJHIDSF2522S.ASD2585545454525";
		}

		return "Username not found";
	}

}
