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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticação", description = "API de autenticação do Sistema")
@RestController
@RequestMapping("/api")
public class AuthenticationController {

	@Autowired
	private SecuriyService service;

	@Autowired
	private UserService userService;

	@Operation(
		      summary = "Login",
		      description = "Realiza o login de um usuário")
	 @ApiResponses({
	      @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
	      @ApiResponse(responseCode = "401", description = "Invalid login or password")})
	@PostMapping("/signin")
	public ResponseEntity<UserAuthDTO> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
		String token = this.service.authenticate(authRequestDTO.login(), authRequestDTO.password());
		UserAuthDTO userAuthDTO = new UserAuthDTO(LoggedUser.getUser(), token);
		return ResponseEntity.ok(userAuthDTO);
	}

	@Operation(
		      summary = "Usuário logado",
		      description = "Retorna as informações do usuário logado")
	 @ApiResponses({
	      @ApiResponse(responseCode = "401", description = "Unauthorized"),
	      @ApiResponse(responseCode = "401", description = "Unauthorized - invalid session")})
	@PostMapping("/me")
	public ResponseEntity<UserMeDTO> me() {
		User user = this.userService.findById(LoggedUser.getUser().getId()).get();
		UserMeDTO userMeDTO = new UserMeDTO(user);
		return ResponseEntity.ok(userMeDTO);
	}
}
