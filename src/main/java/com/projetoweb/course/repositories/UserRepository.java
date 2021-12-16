package com.projetoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoweb.course.entities.User;

//A anotação Repository é opcional, não precisa pq já está estentendo a classe
public interface UserRepository extends JpaRepository<User, Long>{

}
