package com.projetoweb.course.services.exceptions;

//A camada de serviço terá que ser capaz de lançar exceções dela e não deixar estourar exceções diversas
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id " + id);		//Mensagem padrão da exceção personalizada
	}
}
