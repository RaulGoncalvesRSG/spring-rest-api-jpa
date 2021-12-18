package com.projetoweb.course.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projetoweb.course.entities.User;

//A anotação Repository é opcional, não precisa pq já está estentendo a classe
public interface UserRepository extends JpaRepository<User, Long>{
	
	//Consula JPQL. Sql equivalente: WHERE name LIKE '%nome%'
	@Query("SELECT obj FROM User obj WHERE LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%'))")
	Page<User> searchName(String name, Pageable pageable);

	Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
