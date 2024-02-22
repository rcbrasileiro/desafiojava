package com.desafio.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.api.domain.User;
import com.desafio.api.security.service.SecuriyService;
import com.desafio.api.service.UserService;
import com.desafio.api.util.LoggedUser;
import com.desafio.api.web.dto.AuthRequestDTO;
import com.desafio.api.web.dto.UserAuthDTO;
import com.desafio.api.web.dto.UserMeDTO;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

	@Autowired
	private SecuriyService service;

	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<UserAuthDTO> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
		String token = this.service.authenticate(authRequestDTO.login(), authRequestDTO.password());
		UserAuthDTO userAuthDTO = new UserAuthDTO(LoggedUser.getUser(), token);
		return ResponseEntity.ok(userAuthDTO);
	}

	@PostMapping("/me")
	public ResponseEntity<UserMeDTO> me() {
		User user = this.userService.findById(LoggedUser.getUser().getId()).get();
		UserMeDTO userMeDTO = new UserMeDTO(user);
		return ResponseEntity.ok(userMeDTO);
	}
}
