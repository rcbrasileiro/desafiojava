package com.desafio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.api.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByLogin(String username);
	
	boolean existsByEmail(String email);
	
	boolean existsByLogin(String login);

}
