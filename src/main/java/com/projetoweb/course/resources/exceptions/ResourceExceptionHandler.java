package com.projetoweb.course.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.projetoweb.course.services.exceptions.DatabaseException;
import com.projetoweb.course.services.exceptions.ResourceNotFoundException;

//Classe para receber o tratamento manual - faz um método para cada tipo de erro (classe criada)
@ControllerAdvice //Intercepta as execções q acontecerem para q este obj possa executar o possível tratamento
public class ResourceExceptionHandler {

	/*O método intercepta qualquer exceção que for lançada do tipo ResourceNotFoundException e faz o 
	tratamento*/
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resouceNotFound(ResourceNotFoundException e, 
			HttpServletRequest request){
		
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError sError = new StandardError(Instant.now(), status.value(), error, e.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(status).body(sError);
	}
	
	//Método para a DatabaseException
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError sError = new StandardError(Instant.now(), status.value(), error, e.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(status).body(sError);
	}
}
