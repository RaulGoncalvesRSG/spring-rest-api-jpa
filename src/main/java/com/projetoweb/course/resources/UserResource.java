package com.projetoweb.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projetoweb.course.entities.User;
import com.projetoweb.course.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	/*Como está usando o Autowired, UserService precisa ser um componente do Spring para que ele possa ser
	injetado automaticamente*/
	@Autowired 		
	private UserService service;	
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		//ResponseEntity - tipo específico do Spring para retornar respostas de requisição Web
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);			//Retorna no formato padrão Json
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id){
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping 	//@RequestBody - O obj vai chegar como Json na requisição e esse Json será convertido para User										
	public ResponseEntity<User> insert (@RequestBody User obj){
		obj = service.insert(obj);
		
		//Forma adequada de inserir um recurso no BD. Retorna um Status 201 informando que um recurso foi criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
		//return ResponseEntity.ok().body(obj);		//Funciona sem URI, mas não é a forma mais adequada
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id){
		service.delete(id);			
		//Resposta vazia que possui um cabeçalho sem conteúdo. Status 204
		return ResponseEntity.noContent().build();	
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update (@PathVariable Long id, @RequestBody User obj){
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
	//http://localhost:8080/users/search-name?name=alex
	@GetMapping(value = "/search-name")
	public ResponseEntity<Page<User>> searchByName(
			@RequestParam(value = "name", defaultValue = "") String name,
			Pageable pageable){
		
		Page<User> list = service.searchName(name, pageable);
		return ResponseEntity.ok().body(list);
	}
}
