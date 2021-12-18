package com.projetoweb.course.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projetoweb.course.entities.Product;
import com.projetoweb.course.repositories.ProductRepository;

@Service 			
public class ProductService implements GenericService<Product, Long>{

	@Autowired
	private ProductRepository repository;
	
	@Override
	public JpaRepository<Product, Long> getRepository() {
		return repository;
	}
	
	@Transactional(readOnly = true)			//Busca de forma paginada
	public Page<Product> find(PageRequest pageRequest){
		Page<Product> page = repository.findAll(pageRequest);
		
		/*page.stream() para transformar o Page em um List. Com essa chamada do findProductsCategories, 
		a próxima busca das categorias dentro dos produtos será em memória e n irá precisar ir no BD
		 pq o JPA faz cache dos objs*/
		repository.findProductsCategories(page.stream().collect(Collectors.toList()));
		return page;
	}

	public Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable) {
		return repository.findByPriceBetween(minPrice, maxPrice, pageable);
	}
}
