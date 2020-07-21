package com.ss.springboot.api.jwt;

import static com.ss.springboot.api.constant.SecurityConstant.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ss.springboot.api.constant.SecurityConstant;


@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter{

	private JwtTokenProvider provider ; 

	
	
	public JwtAuthorizationFilter(JwtTokenProvider provider) {
		this.provider = provider;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getMethod().equalsIgnoreCase(OPTION_HTTP_METHOD)) {
			response.setStatus(HttpStatus.OK.value());
		}else {
			String authorizationHeader  = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authorizationHeader == null || authorizationHeader.startsWith(TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}
			String token = authorizationHeader.substring(TOKEN_PREFIX.length());
			String username = provider.getSubject(token);
			if(provider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
				List<GrantedAuthority> authorities = provider.getAuthorities(token);
				Authentication authentication = provider.getAuthentication(username, authorities, request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}else {
				SecurityContextHolder.clearContext();
			}
			
		}
		filterChain.doFilter(request, response);
		
	}

}
