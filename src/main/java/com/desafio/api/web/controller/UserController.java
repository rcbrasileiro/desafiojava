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

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<UserResultDTO> save(@RequestBody @Validated UserFormDTO userFormDTO,
			HttpServletRequest request) {
		User user = this.userService.save(User.buildToUser(userFormDTO));

		String requestUrl = request.getRequestURL().toString();
		String location = requestUrl + "/" + user.getId();

		return ResponseEntity.created(URI.create(location)).body(new UserResultDTO(user));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResultDTO> update(@PathVariable Long id,
			@RequestBody @Validated UserFormDTO userFormDTO) {
		User user = User.buildToUser(userFormDTO);
		user.setId(id);
		user = this.userService.update(user);
		return ResponseEntity.ok(new UserResultDTO(user));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<Page<UserResultDTO>> list(Pageable pageable) {
		Page<User> userPage = this.userService.findAll(pageable);
		return ResponseEntity.ok(userPage.map(UserResultDTO::new));
	}
	
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
