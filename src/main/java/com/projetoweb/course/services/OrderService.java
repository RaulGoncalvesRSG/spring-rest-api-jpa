package com.projetoweb.course.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.projetoweb.course.entities.Order;
import com.projetoweb.course.repositories.OrderRepository;

@Service 			
public class OrderService implements GenericService<Order, Long>{

	@Autowired
	private OrderRepository repository;
	
	@Override
	public JpaRepository<Order, Long> getRepository() {
		return repository;
	}
}
