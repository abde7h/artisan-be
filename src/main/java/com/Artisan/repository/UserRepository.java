package com.Artisan.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Artisan.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
