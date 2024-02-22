package com.desafio.api.web.dto;

import java.util.Date;
import java.util.List;

import com.desafio.api.domain.User;

public record UserMeDTO(String firstName, String lastName, String email, Date birthday, String login, String phone,
		List<CarResultDTO> cars, Date createdAt, Date lastLogin) {
	
	public UserMeDTO(User user) {
		this(user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthday(), user.getLogin(),
				user.getPhone(), user.getCars().stream().map(CarResultDTO::new).toList(), user.getCreatedAt(),
				user.getLastLogin());
	}
}
