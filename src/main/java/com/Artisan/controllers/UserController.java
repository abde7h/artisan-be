package com.Artisan.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Artisan.entities.User;
import com.Artisan.services.UserService;

import lombok.extern.java.Log;

@CrossOrigin(origins = "*")
@Log
@RestController
@RequestMapping(value = "/1.0.0")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user")
	public List<User> getUser() {
		log.info("Request a http://localhost:PORT/1.0.0/user (GET)");
		return userService.findAllUsers();

	}

	@GetMapping("/user/{idUsuario}")
	public Optional<User> findUserById(@PathVariable Integer idUsuario) {
		log.info("Request a http://localhost:PORT/1.0.0/user/" + idUsuario + " (GET)");
		Optional<User> user = userService.findUserById(idUsuario);
		return user;
	}
	
	@GetMapping("/user/email/{email}")
	public Optional<User> findUserByEmail(@PathVariable String email) {
		log.info("Request a http://localhost:PORT/1.0.0/user/" + email + " (GET)");
		Optional<User> user = userService.findUserByEmail(email);
		return user;
	}

	@PostMapping("/user/add")
	public ResponseEntity<ResponseEntity<Object>> saveUser(@RequestBody User user) {
		log.info("Request a http://localhost:PORT/1.0.0/user/add (POST)");
		ResponseEntity<Object> savedUser = userService.saveUser(user);

		return (savedUser != null) ? ResponseEntity.status(HttpStatus.CREATED).body(savedUser)
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@DeleteMapping("/user/delete/{idUsuario}")
	public ResponseEntity<Object> deleteUser(@PathVariable Integer idUsuario) {
		log.info("Request a http://localhost:PORT/1.0.0/user/delete/" + idUsuario + " (DELETE)");
		String result = userService.deleteUser(idUsuario);

		return (result.equals("User eliminado correctamente.")) ? ResponseEntity.noContent().build()
				: ResponseEntity.notFound().build();
	}

	@PatchMapping("/user/update")
	public ResponseEntity<Object> updateUser(@RequestBody Optional<User> userUpdated) {
		return (userUpdated.isPresent()) ? userService.updateUser(userUpdated.get())
				:ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No existe User");
	}
}
