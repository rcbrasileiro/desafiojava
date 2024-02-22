package com.desafio.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.desafio.api.service.UserService;

@Service
public class SecuriyService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	public String authenticate(String login, String password) {
		Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login, password));
		if (authentication.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
			this.userService.updateLastLogin();
			return jwtService.generateToken(login);
		} else {
			throw new UsernameNotFoundException("invalid user request!");
		}
	}

}
