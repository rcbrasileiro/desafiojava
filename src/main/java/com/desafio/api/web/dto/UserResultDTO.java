package com.desafio.api.web.dto;

import java.util.Date;
import java.util.List;

import com.desafio.api.domain.User;

public record UserResultDTO(Long id, String firstName, String lastName, String email, Date birthday, String login,
		String phone, Date createdAt, Date lastLogin, List<CarResultDTO> cars) {
	public UserResultDTO(User user) {
		this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthday(),
				user.getLogin(), user.getPhone(), user.getCreatedAt(), user.getLastLogin(),
				user.getCars().stream().map(CarResultDTO::new).toList());
	}
}
