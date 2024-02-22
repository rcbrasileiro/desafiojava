package com.desafio.api.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(@NotBlank String login, @NotBlank String password) {

}
