package com.projetoweb.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projetoweb.course.entities.Product;
import com.projetoweb.course.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired 		
	private ProductService service;	
	
	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		List<Product> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	//http://localhost:8080/products/page?size=2&sort=name,price,asc
	@GetMapping(value = "/page")
	public ResponseEntity<Page<Product>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size){
		
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Product> list = service.find(pageRequest);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id){
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	//http://localhost:8080/products/search-price?min=100&max=1400
	@GetMapping(value = "/search-price")
	public ResponseEntity<Page<Product>> searchByPrice(
			@RequestParam(value = "min", defaultValue = "0") Double minPrice,
			@RequestParam(value = "max", defaultValue = "10000000") Double maxPrice,
			Pageable pageable){
		
		Page<Product> list = service.findByPriceBetween(minPrice, maxPrice, pageable);
		return ResponseEntity.ok().body(list);
	}
}
