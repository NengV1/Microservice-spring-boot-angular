package com.ss.springboot.api.exception;

import lombok.NoArgsConstructor;


public class EmailNotFoundException extends Exception {
	public  EmailNotFoundException(String message) {
		super(message);
	}
}
