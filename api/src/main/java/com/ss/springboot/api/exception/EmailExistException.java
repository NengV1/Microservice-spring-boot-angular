package com.ss.springboot.api.exception;

import lombok.NoArgsConstructor;


public class EmailExistException extends Exception {
	public  EmailExistException(String message) {
		super(message);
	}
}
