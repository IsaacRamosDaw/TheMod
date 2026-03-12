package com.proyectoIntermodular.Controller;

import com.proyectoIntermodular.Models.Pack;
import com.proyectoIntermodular.Models.User;
import com.proyectoIntermodular.Models.Mod;
import com.proyectoIntermodular.Repository.PackRep;
import com.proyectoIntermodular.Repository.UserRep;
import com.proyectoIntermodular.Repository.ModRep;
import com.proyectoIntermodular.Exception.Packs.PackNotFoundException;
import com.proyectoIntermodular.Exception.User.UserNotFoundException;
import com.proyectoIntermodular.Exception.Mod.ModNotFoundException;
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
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/pack")
public class PackController {

	@Autowired
	private PackRep packRepository;

	@Autowired
	private UserRep userRepository;

	@Autowired
	private ModRep modRepository;

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> newPack(@RequestBody Pack pack) {
		if (pack.getName() == null || pack.getName().isBlank()) {
			return buildErrorResponse("El nombre del pack es obligatorio");
		}
		if (pack.getDescription() == null || pack.getDescription().isBlank()) {
			return buildErrorResponse("La descripción del pack es obligatoria");
		}

		if (packRepository.findByName(pack.getName()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
					"success", false,
					"message", "Este pack ya existe"));
		}

		if (pack.getAuthor() == null || pack.getAuthor().getId() == null) {
			return buildErrorResponse("El autor del pack es obligatorio");
		}

		User author = userRepository.findById(pack.getAuthor().getId())
				.orElseThrow(() -> new UserNotFoundException(pack.getAuthor().getId()));

		pack.setAuthor(author);
		Pack savedPack = packRepository.save(pack);

		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
				"success", true,
				"message", "Pack creado exitosamente",
				"data", savedPack));
	}

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllPacks() {
		List<Pack> packs = packRepository.findAll();
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Lista de packs recuperada exitosamente",
				"data", packs));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getPackById(@PathVariable Long id) {
		Pack pack = packRepository.findById(id)
				.orElseThrow(() -> new PackNotFoundException(id));
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Pack encontrado",
				"data", pack));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updatePack(@PathVariable Long id, @RequestBody Pack pack) {
		if (pack.getName() == null || pack.getName().isBlank()) {
			return buildErrorResponse("El nombre del pack es obligatorio");
		}

		Pack existingPack = packRepository.findById(id)
				.orElseThrow(() -> new PackNotFoundException(id));

		if (!existingPack.getName().equals(pack.getName())) {
			if (packRepository.findByName(pack.getName()).isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
						"success", false,
						"message", "Ya existe otro pack con ese nombre"));
			}
		}

		existingPack.setName(pack.getName());
		existingPack.setDescription(pack.getDescription());
		Pack updatedPack = packRepository.save(existingPack);

		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Pack actualizado exitosamente",
				"data", updatedPack));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deletePack(@PathVariable Long id) {
		if (!packRepository.existsById(id)) {
			throw new PackNotFoundException(id);
		}
		packRepository.deleteById(id);
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Pack eliminado exitosamente"));
	}

	@PostMapping("/{packId}/mod/{modId}")
	public ResponseEntity<Map<String, Object>> addModToPack(@PathVariable Long packId, @PathVariable Long modId) {
		Pack pack = packRepository.findById(packId)
				.orElseThrow(() -> new PackNotFoundException(packId));
		Mod mod = modRepository.findById(modId)
				.orElseThrow(() -> new ModNotFoundException(modId));

		pack.addMod(mod);
		packRepository.save(pack);

		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Mod añadido al pack"));
	}

	@DeleteMapping("/{packId}/mod/{modId}")
	public ResponseEntity<Map<String, Object>> removeModFromPack(@PathVariable Long packId, @PathVariable Long modId) {
		Pack pack = packRepository.findById(packId)
				.orElseThrow(() -> new PackNotFoundException(packId));
		Mod mod = modRepository.findById(modId)
				.orElseThrow(() -> new ModNotFoundException(modId));

		pack.removeMod(mod);
		packRepository.save(pack);

		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Mod eliminado del pack"));
	}

	private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
				"success", false,
				"message", message));
	}
}
