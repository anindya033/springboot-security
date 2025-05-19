package com.crafter.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crafter.security.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	// Find user by username (for login or security purposes)
	//Optional<UserEntity> findByUsername(String username);
	
	UserEntity findByUsername(String username);

	// You can add other query methods if needed
}