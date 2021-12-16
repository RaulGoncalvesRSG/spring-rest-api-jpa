package com.projetoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoweb.course.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
