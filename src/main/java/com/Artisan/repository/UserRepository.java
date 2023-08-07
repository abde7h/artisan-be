package com.Artisan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Artisan.entities.User;





public interface UserRepository extends JpaRepository<User, Integer> {
	
    @Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> findByEmail(String email);
    
}
