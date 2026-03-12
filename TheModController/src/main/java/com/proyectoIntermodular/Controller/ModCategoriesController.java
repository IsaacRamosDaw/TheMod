package com.proyectoIntermodular.Controller;

import com.proyectoIntermodular.Models.ModCategories;
import com.proyectoIntermodular.Repository.ModCatRep;
import com.proyectoIntermodular.Exception.Mod.ModCategoryNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/mod-category")
public class ModCategoriesController {

	@Autowired
	private ModCatRep categoryRepository;

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> newCategory(@RequestBody ModCategories category) {
		if (category.getName() == null || category.getName().isBlank()) {
			return buildErrorResponse("El nombre de la categoría es obligatorio");
		}

		if (categoryRepository.findByName(category.getName()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
					"success", false,
					"message", "Esta categoría ya existe"));
		}

		ModCategories savedCategory = categoryRepository.save(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
				"success", true,
				"message", "Categoría de mod creada exitosamente",
				"data", savedCategory));
	}

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllCategories() {
		List<ModCategories> categories = categoryRepository.findAll();
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Lista de categorías de mods recuperada exitosamente",
				"data", categories));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long id) {
		ModCategories category = categoryRepository.findById(id)
				.orElseThrow(() -> new ModCategoryNotFoundException(id));
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Categoría de mod encontrada",
				"data", category));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable Long id, @RequestBody ModCategories category) {
		if (category.getName() == null || category.getName().isBlank()) {
			return buildErrorResponse("El nombre de la categoría es obligatorio");
		}

		ModCategories existingCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new ModCategoryNotFoundException(id));

		if (!existingCategory.getName().equals(category.getName())) {
			if (categoryRepository.findByName(category.getName()).isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
						"success", false,
						"message", "Ya existe otra categoría de mod con ese nombre"));
			}
		}

		existingCategory.setName(category.getName());
		ModCategories updatedCategory = categoryRepository.save(existingCategory);

		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Categoría de mod actualizada exitosamente",
				"data", updatedCategory));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Long id) {
		if (!categoryRepository.existsById(id)) {
            throw new ModCategoryNotFoundException(id);
        }
		categoryRepository.deleteById(id);
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Categoría de mod eliminada exitosamente"));
	}

	private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
				"success", false,
				"message", message));
	}
}
