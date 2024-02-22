package com.desafio.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.desafio.api.domain.User;

public interface UserService {
	
	User save(User user);
	
	User update(User user);
	
	Optional<User> findById(Long id);
	
	Page<User> findAll(Pageable pageable);
	
	void delete(Long id);
	
	void updateLastLogin();
	
}
