package com.desafio.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Desafio técnico java - Controle de acesso JWT e Spring Security",
version = "1", description = "API RESTFULL para gestão de carros e usuário"))
public class DesafioJavaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioJavaApiApplication.class, args);
	}

}
