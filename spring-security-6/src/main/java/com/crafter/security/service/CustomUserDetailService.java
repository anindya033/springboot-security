package com.crafter.security.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.crafter.security.CustomUserDetails;
import com.crafter.security.entity.UserEntity;
import com.crafter.security.repo.UserRepository;

@Component
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private final UserRepository userRepo;
	
	public CustomUserDetailService(UserRepository repo) {
		this.userRepo = repo;
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity user = userRepo.findByUsername(username);
		
		if (Objects.isNull(user)) {
			System.out.println("User is not available");
			throw new UsernameNotFoundException("User is not available");
		}
		return new CustomUserDetails(user);
	}

}
