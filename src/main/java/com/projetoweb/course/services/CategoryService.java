package com.projetoweb.course.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projetoweb.course.entities.Category;
import com.projetoweb.course.repositories.CategoryRepository;

@Service 			
public class CategoryService implements GenericService<Category, Long> {

	@Autowired
	private CategoryRepository repository;

	@Override
	public JpaRepository<Category, Long> getRepository() {
		return repository;
	}

	@Transactional(readOnly = true)
	public Page<Category> find(PageRequest pageRequest){
		return repository.findAll(pageRequest);
	}
}
