package com.desafio.api.web.dto;

import java.util.Date;

import com.desafio.api.domain.User;

public record UserAuthDTO(Long id, String firstName, String lastName, String email, Date birthday, String login,
		String phone, Date createdAt, Date lastLogin, String token) {

	public UserAuthDTO(User user, String token) {
		this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthday(),
				user.getLogin(), user.getPhone(), user.getCreatedAt(), user.getLastLogin(), token);
	}
}
