package com.Artisan.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Artisan.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
