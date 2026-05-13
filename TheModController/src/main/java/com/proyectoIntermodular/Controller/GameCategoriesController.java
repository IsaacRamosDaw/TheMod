package com.proyectoIntermodular.Controller;

import com.proyectoIntermodular.Models.GameCategories;
import com.proyectoIntermodular.Repository.GameCatRep;
import com.proyectoIntermodular.Exception.Game.GameCategoryNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game-category")
public class GameCategoriesController {

	@Autowired
	private GameCatRep categoryRepository;

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> newCategory(@RequestBody GameCategories category) {
		if (category.getName() == null || category.getName().isBlank()) {
			return buildErrorResponse("El nombre de la categoría es obligatorio");
		}

		if (categoryRepository.findByName(category.getName()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
					"success", false,
					"message", "Esta categoría ya existe"));
		}

		GameCategories savedCategory = categoryRepository.save(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
				"success", true,
				"message", "Categoría creada exitosamente",
				"data", savedCategory));
	}

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllCategories() {
		List<GameCategories> categories = categoryRepository.findAll();
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Lista de categorías recuperada exitosamente",
				"data", categories));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long id) {
		GameCategories category = categoryRepository.findById(id)
				.orElseThrow(() -> new GameCategoryNotFoundException(id));
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Categoría encontrada",
				"data", category));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable Long id, @RequestBody GameCategories category) {
		if (category.getName() == null || category.getName().isBlank()) {
			return buildErrorResponse("El nombre de la categoría es obligatorio");
		}

		GameCategories existingCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new GameCategoryNotFoundException(id));

		if (!existingCategory.getName().equals(category.getName())) {
			if (categoryRepository.findByName(category.getName()).isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
						"success", false,
						"message", "Ya existe otra categoría con ese nombre"));
			}
		}

		existingCategory.setName(category.getName());
		GameCategories updatedCategory = categoryRepository.save(existingCategory);

		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Categoría actualizada exitosamente",
				"data", updatedCategory));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Long id) {
		if (!categoryRepository.existsById(id)) {
			throw new GameCategoryNotFoundException(id);
		}
		categoryRepository.deleteById(id);
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Categoría eliminada exitosamente"));
	}

	private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
				"success", false,
				"message", message));
	}
}
