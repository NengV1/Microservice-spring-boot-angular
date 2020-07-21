package com.ss.springboot.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,updatable = false)
	private Long id;
	private String userId;
	private String firstName;
	private String lastName;
	private String username;	
	private String password;
	private String email;
	private String profileiImage;
	private Date lastLoginDate;
	private Date lastLoginDateDisplay;
	private Date joinDate;
	private String role;
	private String[] authorities;
	private boolean isActive;
	private boolean isNotLocked;
	

	

 }
