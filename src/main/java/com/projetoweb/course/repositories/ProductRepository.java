package com.projetoweb.course.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projetoweb.course.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	/*JOIN FETCH para eliminar o problema do N + 1. Qnd faz o JOIN FETCH não aceita o retorno Page, 
	apenas List. "IN" referencia apenas os produtos presentes na Lista do parâmetro*/
	@Query("SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj IN :products")
	List<Product> findProductsCategories(List<Product> products);
}
