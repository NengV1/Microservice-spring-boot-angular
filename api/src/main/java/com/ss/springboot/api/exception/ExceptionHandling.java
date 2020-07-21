package com.ss.springboot.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

import javax.persistence.NoResultException;

import com.ss.springboot.api.response.HttpResponse;

@RestControllerAdvice
public class ExceptionHandling {//extends ResponseEntityExceptionHandler{

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final String ACCOUNT_LOCKED = "Your account been locked. please contract admin";
	private static final String METHOD_IS_NOT_ALLOWED = "This request is not allowed";
	private static final String INTERNAL_SERVER_ERROR_MSG = "An error occued while processing the request";
	private static final String INCORRECT_CREDENTIALS = "Username / password incorrect,try again";
	private static final String ACCOUNT_DISABLED = "Yot account has been disabled.";
	private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
	private static final String NOT_ENOUGH_PREMISSION = "You do not have enough permission"; 
	public static final String ERROR_PATH = "/error";
	
	
	private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus,String message){
		HttpResponse httpResponse = new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),message.toUpperCase());
		return new ResponseEntity<>(httpResponse,httpStatus);
	}
	
	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<HttpResponse> accountDisabledException(){
		return createHttpResponse(HttpStatus.BAD_REQUEST, ACCOUNT_DISABLED);
	}
	
	@ExceptionHandler(UsernameExistException.class)
	public ResponseEntity<HttpResponse> usernameExistException(UsernameExistException exception){
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	@ExceptionHandler(EmailExistException.class)
	public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception){
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception){
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception){
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
//	 @RequestMapping(ERROR_PATH)
//	    public ResponseEntity<HttpResponse> notFound404() {
//	        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
//	    }
	 
	 @ExceptionHandler(NoResultException.class)
	    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
	        LOGGER.error(exception.getMessage());
	        return createHttpResponse(NOT_FOUND, exception.getMessage());
	    }
	 
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
	        LOGGER.error(exception.getMessage());
	        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
	    }
	 
//	@Override
//	protected ResponseEntity<Object> handleExceptionInternal(
//			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//		return  createHttpResponse(status,ex.getMessage());
//	}
}
