package com.ss.springboot.api.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ss.springboot.api.entity.User;
import com.ss.springboot.api.exception.UsernameExistException;

public interface UserService {

	User register(String firstname,String lastname,String username,String email,String password) throws UsernameExistException;
	
	List<User> getUsers();
	
	User findUserByUsername(String username);
	
	User findUserByEmail(String email);
	
	User addNewUser(String firstname,String lastname,String username,String email,String password,String role,boolean isNonLock, boolean isActive,MultipartFile profileImg);
}
