package com.ss.springboot.api.exception;

import lombok.NoArgsConstructor;


public class UsernameExistException extends Exception {
	
	public  UsernameExistException(String message) {
		super(message);
	}
}
