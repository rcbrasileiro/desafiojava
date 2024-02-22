package com.desafio.api.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.desafio.api.security.UserDetailsServiceImpl;
import com.desafio.api.security.service.JwtService;
import com.desafio.api.web.exception.RestError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	private static final String[] IGNORED_URLS = { "/h2-console/", "/api/users", "/api/signin" };

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestUri = request.getRequestURI();
		if (Arrays.stream(IGNORED_URLS).anyMatch(requestUri::startsWith)) {
			filterChain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			try {
				username = this.jwtService.extractUsername(token);

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

		if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
			UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);
			if (this.jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
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
