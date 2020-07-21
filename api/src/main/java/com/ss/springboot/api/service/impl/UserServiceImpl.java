package com.ss.springboot.api.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ss.springboot.api.entity.User;
import com.ss.springboot.api.entity.UserPrincipal;
import com.ss.springboot.api.enums.Role;
import com.ss.springboot.api.exception.UsernameExistException;
import com.ss.springboot.api.repository.UserRepository;
import com.ss.springboot.api.service.UserService;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService,UserDetailsService{

	private UserRepository userRepository;
	private Logger LOGGER = org.slf4j.LoggerFactory.getLogger(getClass());
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if(user == null) {
			LOGGER.error("user not found " + username);
			throw new UsernameNotFoundException("user not found " + username);
		}else {
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info("Return found user " + username);
			return userPrincipal;
		}

	}

	@Override
	public User register(String firstName, String lastName, String username, String email,String password) throws UsernameExistException {
		validateNewUserNameAndEmail(StringUtils.EMPTY, username, email);
		User user = new User();
	//	user.setUserId(userId);
		//String password = generatePassword();
		String encodedPassword = encodePassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setJoinDate(new Date());
		user.setPassword(encodedPassword);
		user.setActive(true);
		user.setNotLocked(true);
		user.setRole(Role.ROLE_USER.name());
		user.setAuthorities(Role.ROLE_USER.getAuthorities());
		user.setProfileiImage(getTemporaryProfileImageUrl());
		userRepository.save(user);
		LOGGER.info("New user");
		
		
		
		return user;
	}
	
	private String getTemporaryProfileImageUrl() {
		
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/profile/temp").toString();
	}

	private String encodePassword(String password) {
		
		return passwordEncoder.encode(password);
	}

	private String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(10);
	};
	

	private User validateNewUserNameAndEmail(String currentUsername,String newUsername, String email) throws UsernameExistException {
		if(StringUtils.isNotBlank(currentUsername)) {
			User currentUser = findUserByUsername(currentUsername);
			if(currentUser == null) {
				throw new UsernameNotFoundException("No user found by username : " + currentUsername);
			}
			
			User userByUsername = findUserByUsername(newUsername);
			if(userByUsername != null && userByUsername.getId().equals(currentUser.getId())) {
				throw new UsernameNotFoundException("Username already exists");
			}
			
			
			User userByEmail = findUserByEmail(email);
			if(userByEmail != null && userByEmail.getId().equals(currentUser.getId())) {
				throw new UsernameNotFoundException("Username already exists");
			}
			
			return  currentUser;
		
		} else {
			
			User userByUsername = findUserByUsername(newUsername);
			if(userByUsername != null) {
				throw new UsernameExistException("Username already exists");
			}
			
			
			User userByEmail = findUserByEmail(email);
			if(userByEmail != null ) {
				throw new UsernameExistException("Username already exists");
			}
			
		}
		return null;
		
	}

	@Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

	@Override
	public User addNewUser(String firstname, String lastname, String username, String email, String password,
			String role, boolean isNonLock, boolean isActive, MultipartFile profileImg) {
		// TODO Auto-generated method stub
		return null;
	}

}
