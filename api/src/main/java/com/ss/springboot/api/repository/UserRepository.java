package com.ss.springboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.springboot.api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findUserByUsername(String username);
	
	User findUserByEmail(String email);
}
