package com.Artisan.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Artisan.entities.Product;
import com.Artisan.services.ProductService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/1.0.0")

public class ProductController {

	ProductService productService;

	public ProductController(ProductService productService) {

		this.productService = productService;

	}

	@RequestMapping("/product")
	public List<Product> getProduct() {

		log.info("Request a http://localhost:PORT/1.0.0/product (GET)");
		return productService.findAllProducts();

	}

	@RequestMapping("/product/{idProduct}")
	public Optional<Product> findProductById(@PathVariable Long idProduct) {
		
		log.info("Request a http://localhost:PORT/1.0.0/product/" + idProduct + " (GET)");
		Optional<Product> product = productService.findProductById(idProduct);
		return product;

	}

	@PostMapping("/product/add")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		
		log.info("Request a http://localhost:PORT/1.0.0/product/add (POST)");
		Product savedProduct = productService.saveProduct(product);
		return (savedProduct != null) ? ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

	@DeleteMapping("/product/delete/{idProduct}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Long idProduct) {
		
		log.info("Request a http://localhost:PORT/1.0.0/product/delete/" + idProduct + " (DELETE)");
		String result = productService.deleteProduct(idProduct);
		
		return (result.equals("Producto eliminado correctamente.")) ? ResponseEntity.noContent().build()
				: ResponseEntity.notFound().build();

	}

	@PatchMapping("/product/update")
	public ResponseEntity<String> updateProduct(@RequestBody Product productUpdated) {
		
		log.info("Request a http://localhost:PORT/1.0.0/product/update (PATCH)");
		Long productId = (long) productUpdated.getProduct_id();
		Optional<Product> existingProduct = productService.findProductById(productId);
		
		if (existingProduct.isPresent()) {
			
			productService.updateProduct(productUpdated);
			return ResponseEntity.ok("Producto modificado");
			
		} else {
			
			return ResponseEntity.notFound().build();
			
		}
	}

}