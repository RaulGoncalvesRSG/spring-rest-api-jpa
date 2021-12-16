package com.projetoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoweb.course.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
