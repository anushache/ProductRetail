package com.example.demo.ExceptionHandler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<String> validationException(ValidationException validException)
	{
		return new ResponseEntity<String>("The product price should not exceed 10000",HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> nosuchElementHandler(NoSuchElementException nosuchElement)
	{
		return new ResponseEntity<String>("The product is not available ,please give available product id ",HttpStatus.NOT_FOUND);
		
	}
}