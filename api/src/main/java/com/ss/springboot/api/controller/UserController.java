package com.ss.springboot.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.springboot.api.entity.User;
import com.ss.springboot.api.exception.EmailExistException;
import com.ss.springboot.api.exception.ExceptionHandling;
import com.ss.springboot.api.exception.UsernameExistException;
import com.ss.springboot.api.service.UserService;

@RestController
public class UserController extends ExceptionHandling {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}


	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user)throws UsernameNotFoundException,UsernameExistException,EmailExistException {
		User loginUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(),user.getPassword());
		
		return new ResponseEntity<>(loginUser,HttpStatus.OK);
	}
	
}
