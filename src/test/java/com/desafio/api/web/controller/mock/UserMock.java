package com.desafio.api.web.controller.mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import com.desafio.api.domain.User;

public class UserMock {

	public static User create() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1L);
		when(user.getFirstName()).thenReturn("João");
		when(user.getLastName()).thenReturn("Silva");
		when(user.getEmail()).thenReturn("joao@example.com");
		LocalDate birthday = LocalDate.of(1990, Month.JANUARY, 1);
		Date date = Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant());
		when(user.getBirthday()).thenReturn(date);
		when(user.getLogin()).thenReturn("joaosilva");
		when(user.getPassword()).thenReturn("$2a$12$4AOuyX3CZVD8n7Bk9aP2FO/YNxSWKZB7BpUlum/h0QuPJ1ZU1hnmK");
		when(user.getPhone()).thenReturn("(12) 3456-7890");
		return user;
	}
}
