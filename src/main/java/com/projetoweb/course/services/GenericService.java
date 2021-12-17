package com.projetoweb.course.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//Tipo do Objeto e ID são genéricos
public interface GenericService<T, ID> {

	JpaRepository<T, ID> getRepository();
	
	default List<T> findAll(){
		return getRepository().findAll();
	}
	
	default T findById(ID id) {
		return getRepository().findById(id).get();
	}
	
	default void delete(ID id) {
		getRepository().deleteById(id);
	}
}
