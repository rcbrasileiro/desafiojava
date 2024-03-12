package com.desafio.api.security.util;

public class Access {
	
	public static final String[] IGNORED_URLS = { "/h2-console/", "/api/users", "/api/signin", "/swagger-ui", "/v3/api-docs" };
	
	public static final String[] PUBLIC_ACCESS = { "/h2-console/**", "/api/users/**", "/api/signin", "/swagger-ui/**", "/v3/api-docs/**" };

}
