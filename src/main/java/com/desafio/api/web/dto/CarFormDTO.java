package com.desafio.api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarFormDTO(@NotNull Integer year, @NotBlank String licensePlate, @NotBlank String model,
		@NotBlank String color) {
}
