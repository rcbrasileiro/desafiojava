package com.desafio.api.web.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.api.domain.User;
import com.desafio.api.service.UserService;
import com.desafio.api.web.dto.UserFormDTO;
import com.desafio.api.web.dto.UserResultDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Gestão de usuário", description = "API de gestão dos usuários")
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

	@Autowired
	private UserService userService;
	
	@Operation(
		      summary = "Salvar",
		      description = "Salva um novo usuário")
	 @ApiResponses({
	      @ApiResponse(responseCode = "409", description = "Email already exists"),
	      @ApiResponse(responseCode = "409", description = "Login already exists"),
	      @ApiResponse(responseCode = "400", description = "Invalid fields"),
	      @ApiResponse(responseCode = "402", description = "Missing fields")})
	@PostMapping
	public ResponseEntity<UserResultDTO> save(@RequestBody @Validated UserFormDTO userFormDTO,
			HttpServletRequest request) {
		User user = this.userService.save(User.buildToUser(userFormDTO));

		String requestUrl = request.getRequestURL().toString();
		String location = requestUrl + "/" + user.getId();

		return ResponseEntity.created(URI.create(location)).body(new UserResultDTO(user));
	}

	@Operation(
		      summary = "Atualizar",
		      description = "Atualizar um usuário")
	 @ApiResponses({
	      @ApiResponse(responseCode = "409", description = "Email already exists"),
	      @ApiResponse(responseCode = "409", description = "Login already exists"),
	      @ApiResponse(responseCode = "400", description = "Invalid fields"),
	      @ApiResponse(responseCode = "402", description = "Missing fields")})
	@PutMapping("/{id}")
	public ResponseEntity<UserResultDTO> update(@PathVariable Long id,
			@RequestBody @Validated UserFormDTO userFormDTO) {
		User user = User.buildToUser(userFormDTO);
		user.setId(id);
		user = this.userService.update(user);
		return ResponseEntity.ok(new UserResultDTO(user));
	}

	@Operation(
		      summary = "Deletar",
		      description = "Deletar um usuário")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(
		      summary = "Listar",
		      description = "Listar todos os usuários")	 
	@GetMapping
	public ResponseEntity<Page<UserResultDTO>> list(Pageable pageable) {
		Page<User> userPage = this.userService.findAll(pageable);
		return ResponseEntity.ok(userPage.map(UserResultDTO::new));
	}
	
	@Operation(
		      summary = "Buscar",
		      description = "Buscar por id")
	@GetMapping("/{id}")
    public ResponseEntity<UserResultDTO> findById(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(new UserResultDTO(userOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
