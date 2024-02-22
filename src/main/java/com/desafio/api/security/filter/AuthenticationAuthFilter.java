package com.desafio.api.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.desafio.api.security.service.JwtService;
import com.desafio.api.security.util.Access;
import com.desafio.api.web.exception.RestError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestUri = request.getRequestURI();
		if (Arrays.stream(Access.IGNORED_URLS).anyMatch(requestUri::startsWith)) {
			filterChain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader("Authorization");
		String token = null;
		if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
			try {
				token = authHeader.substring(7);
				this.jwtService.extractUsername(token);

			} catch (ExpiredJwtException e) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getWriter().write(convertObjectToJson(
						new RestError("Unauthorized - invalid session", HttpStatus.UNAUTHORIZED.value())));
				return;
			}
		}

		if (Objects.isNull(token)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter()
					.write(convertObjectToJson(new RestError("Unauthorized", HttpStatus.UNAUTHORIZED.value())));
			return;
		}
		filterChain.doFilter(request, response);
	}

	private String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
}
