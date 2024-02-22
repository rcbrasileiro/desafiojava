package com.desafio.api.util;

import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;

import com.desafio.api.domain.User;
import com.desafio.api.security.model.UserDetails;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoggedUser {
	
	public static User getUser() {

		User user = null;

		if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = userDetails.getUser();
		}
		return user;
	}
}
