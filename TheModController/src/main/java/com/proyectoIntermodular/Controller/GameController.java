package com.proyectoIntermodular.Controller;

import com.proyectoIntermodular.Models.Game;
import com.proyectoIntermodular.Repository.GameRep;
import com.proyectoIntermodular.Exception.Game.GameNotFoundException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {

	@Autowired
	private GameRep gameRepository;

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> newGame(@RequestBody Game game) {

		if (game.getName() == null || game.getName().isBlank()) {
			return buildErrorResponse("El nombre del juego es obligatorio");
		}

		if (gameRepository.findByName(game.getName()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
					"success", false,
					"message", "Este juego ya está registrado"));
		}

		try {
			Game savedGame = gameRepository.save(game);

			return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
					"success", true,
					"message", "Juego creado exitosamente",
					"data", savedGame));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
					"success", false,
					"message", "Error al guardar el juego"));
		}
	}

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllGames() {
		List<Game> games = gameRepository.findAll();
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Lista de juegos recuperada exitosamente",
				"data", games));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getGameById(@PathVariable Long id) {
		Game game = gameRepository.findById(id)
				.orElseThrow(() -> new GameNotFoundException(id));
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Juego encontrado",
				"data", game));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateGame( @PathVariable Long id, @RequestBody Game newGame) {

		Game game = gameRepository.findById(id)
				.orElseThrow(() -> new GameNotFoundException(id));

		if (!game.getName().equals(newGame.getName())) {
			if (gameRepository.findByName(newGame.getName()).isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
						"success", false,
						"message", "Ya existe otro juego con ese nombre"));
			}
		}

		try {
			game.setName(newGame.getName());
			Game updatedGame = gameRepository.save(game);
			return ResponseEntity.ok(Map.of(
					"success", true,
					"message", "Juego actualizado exitosamente",
					"data", updatedGame));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
					"success", false,
					"message", "Error al actualizar el juego"));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteGame(@PathVariable Long id) {
		if (!gameRepository.existsById(id)) {
			throw new GameNotFoundException(id);
		}
		gameRepository.deleteById(id);
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", "Juego eliminado exitosamente"));
	}

	private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
				"success", false,
				"message", message));
	}
}