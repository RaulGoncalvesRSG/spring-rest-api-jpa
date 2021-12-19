package com.projetoweb.course.resources.exceptions;

import java.time.Instant;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.projetoweb.course.entities.Field;
import com.projetoweb.course.services.exceptions.DatabaseException;
import com.projetoweb.course.services.exceptions.ResourceNotFoundException;

//Classe para receber o tratamento manual - faz um método para cada tipo de erro (classe criada)
@ControllerAdvice //Intercepta as execções q acontecerem para q este obj possa executar o possível tratamento
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler{
	
	/*MessageSource - interface para resolver mensagens do arquivo "messages"*/
	@Autowired
	private MessageSource messageSource;

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
	
	@Override			//Método sobreescrito. Argumento inválido
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var campos = new ArrayList<Field>();
		
		//Faz uma iteração para obter todos os erros encontrados
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();		//Campo do erro. Ex: nome, telefone
			//LocaleContextHolder.getLocale() coloca a msg no idioma local
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Field(nome, mensagem));
		}
		
		var sError = new StandardError();
		sError.setStatus(status.value());
		sError.setError("Argumento inválido");
		sError.setMessage("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente");
		sError.setTimestamp(Instant.now());
		sError.setCampos(campos);
		
		//Adiciona o problema no body da resposta
		return super.handleExceptionInternal(ex, sError, headers, status, request);
	}
}
