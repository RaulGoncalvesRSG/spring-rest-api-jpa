package com.projetoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoweb.course.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
}
