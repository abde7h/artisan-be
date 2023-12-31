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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Artisan.entities.Artisan;
import com.Artisan.entities.DTOs.ArtisanDTO;
import com.Artisan.entities.DTOs.ArtisanProfileDTO;
import com.Artisan.services.ArtisanService;

import lombok.extern.java.Log;

@CrossOrigin(origins = "http://localhost:3000")
@Log
@RestController
@RequestMapping(value = "/1.0.0")
public class ArtisanController {

	ArtisanService artisanService;

	public ArtisanController(ArtisanService artisanService) {

		this.artisanService = artisanService;

	}

	@GetMapping("/artisan") // Trae una lista con todos los elementos llamados Artisan
	public List<Artisan> getArtisan() {

		log.info("Request a http://localhost:PORT/1.0.0/artisan (GET)");
		return artisanService.findAllArtisans();

	}

	// DTO
	@GetMapping("/artisanDTO")
	public List<ArtisanDTO> getArtisanDTO() { // Trae todos los elementos Artisan filtrados por el DTO username, name,
												// surname, image

		log.info("Request a http://localhost:PORT/1.0.0/artisan (GET)");
		return artisanService.findAllArtisansDTO();

	}

	@GetMapping("/artisan/{idArtisan}") // Trae los elementos Artisan *Input ID*
	public Optional<Artisan> findArtisanById(@PathVariable Integer idArtisan) {
		log.info("Request a http://localhost:PORT/1.0.0/artisan/" + idArtisan + " (GET)");
		Optional<Artisan> artisan = artisanService.findArtisanById(idArtisan);
		return artisan;
	}

	@GetMapping("/artisan/email/{email}") // Trae los elementos Artisan *Input EMAIL*
	public Optional<Artisan> findArtisanByEmail(@PathVariable String email) {
		log.info("Request a http://localhost:PORT/1.0.0/artisan/" + email + " (GET)");
		Optional<Artisan> artisan = artisanService.findArtisanByEmail(email);
		return artisan;
	}

	@PostMapping("/artisan/add") // Añade un nuevo elemento Artisan
	public ResponseEntity<ResponseEntity<Object>> saveArtisan(@RequestBody Artisan artisan) {

		log.info("Request a http://localhost:PORT/1.0.0/artisan/add (POST)");
		ResponseEntity<Object> savedArtisan = artisanService.saveArtisan(artisan);
		return (savedArtisan != null) ? ResponseEntity.status(HttpStatus.CREATED).body(savedArtisan)
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

	@DeleteMapping("/artisan/delete/{idArtisan}") // Borra un elemento Artisan *Input ID*
	public ResponseEntity<Object> deleteUser(@PathVariable Integer idArtisan) {

		log.info("Request a http://localhost:PORT/1.0.0/artisan/delete/" + idArtisan + " (DELETE)");
		String result = artisanService.deleteArtisan(idArtisan);
		return (result.equals("Artesano eliminado correctamente.")) ? ResponseEntity.noContent().build()
				: ResponseEntity.notFound().build();

	}

	@PutMapping("/artisan/update") // Actualiza un determinado elemento Artisan
	public ResponseEntity<Object> updateArtisan(@RequestBody Optional<Artisan> artisanUpdated) {

		return (artisanUpdated.isPresent()) ? artisanService.updateArtisan(artisanUpdated.get())
				: ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No existe User");

	}

	// DTO
	@GetMapping("/artisanProfile/{username}") // Trae el elemento Artisan con su nombre apellidos username descripcion
												// contador de followers y productos y la lista de productos publicados
												// *Input USERNAME*
	public ArtisanProfileDTO profile(@PathVariable String username) {

		return artisanService.artisanProfileDTO(username);

	}

	@GetMapping("/artisan/{email}/{password}")
	public ResponseEntity<Object> getArtisanByEmailAndPassword(@PathVariable String email,
			@PathVariable String password) {

		List<Artisan> artisan = artisanService.findArtisanByEmailAndPassword(email, password);
		return (artisan.size() > 0)?
			ResponseEntity.ok(artisan.get(0)):ResponseEntity.notFound().build();
	}

	@PostMapping("/artisan/photo/upload/{artisanId}")
	public ResponseEntity<String> uploadPhoto(@PathVariable Integer artisanId,
			@RequestParam("file") MultipartFile file) {
		return artisanService.uploadPhoto(artisanId, file);
	}

	@GetMapping("/artisan/photo/{artisanId}")
	public ResponseEntity<List<String>> getArtisanPhotos(@PathVariable Integer artisanId) {
		List<String> photoUrls = artisanService.getArtisanPhotoUrls(artisanId);
		return ResponseEntity.ok(photoUrls);
	}

}
