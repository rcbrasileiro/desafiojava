package com.desafio.api.web.dto;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record UserFormDTO(@NotBlank @Size(max = 255) String firstName,
		@NotBlank @Size(max = 255) String lastName, @NotBlank @Email @Size(max = 255) String email,
		@NotNull @PastOrPresent Date birthday, @NotBlank @Size(max = 255) String login,
		@NotBlank @Size(max = 255) String password, @NotBlank String phone,
		List<CarFormDTO> cars) {
}
