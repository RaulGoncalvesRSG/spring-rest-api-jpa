package com.projetoweb.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projetoweb.course.entities.User;
import com.projetoweb.course.repositories.UserRepository;
import com.projetoweb.course.services.exceptions.DatabaseException;
import com.projetoweb.course.services.exceptions.ResourceNotFoundException;

@Service 			//Pode usar: Component, Repository ou Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();		//Em projeto grande trabalha com paginação para n buscar td
	}
	
	public User findById(Long id) {
		/*services.exceptions.ResourceNotFoundException
		resources.exceptions.StandardError					resources.exceptions.ResourceExceptionHandler
		
		Sem as 3 classes, o erro retornado não será 404(correto), será 500 (forma padrão do Spring)
		Quando não implementar as 3 classes, faz direto: return repository.findById(id).get();*/
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert (User obj){
		return repository.save(obj);
	}
	
	public void delete (Long id){
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
		/*RuntimeException - Tipo mais genérico q irá pegar qualquer tipo de erro de execução
	
	Primeiro utiliza o RuntimeException no catch e coloca o e.printStackTrace para saber qual é
	o erro específico que está acontecendo. O erro encontrado foi o EmptyResultDataAccessException,
	então coloca o catch para tratar essa exceção específica. Este é o caso para um ID não encontrado.
	Se aparecer outro erro, faz o memso processo, coloca RuntimeException com o printStackTrace
	novamente para descobrir o próximo erro e assim poder tratar a exceção específica*/
			
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public User update (Long id, User obj){
		//getOne Prepara o objeto monitorado sem ir no BD, diferente do getById. Essa forma é mais eficiente
		try {
			User entity = repository.getOne(id);
			
			updateData(entity, obj);
			
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
}
