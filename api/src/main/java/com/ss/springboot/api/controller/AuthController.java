package com.ss.springboot.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.springboot.api.constant.SecurityConstant;
import com.ss.springboot.api.entity.User;
import com.ss.springboot.api.entity.UserPrincipal;
import com.ss.springboot.api.exception.ExceptionHandling;
import com.ss.springboot.api.jwt.JwtTokenProvider;
import com.ss.springboot.api.service.UserService;

//@CrossOrigin
@RestController
public class AuthController extends ExceptionHandling {

	private UserService userService;
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;

	
	@Autowired
	public AuthController(UserService userService, AuthenticationManager authenticationManager,
			JwtTokenProvider jwtTokenProvider) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user){
		authenticate(user.getUsername(),user.getPassword());
		User loginUser = userService.findUserByUsername(user.getUsername());
		UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
		return new ResponseEntity<>(loginUser,jwtHeader,HttpStatus.OK);
	
	}
	
	private HttpHeaders getJwtHeader(UserPrincipal user) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
		return headers;
	}

	private void authenticate(String username,String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
	}
	
}
