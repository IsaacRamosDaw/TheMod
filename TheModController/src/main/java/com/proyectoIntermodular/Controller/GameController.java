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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameRep gameRepository;

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/games/";

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> newGame(
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile file) {

        if (name == null || name.isBlank()) {
            return buildErrorResponse("El nombre del juego es obligatorio");
        }

        if (gameRepository.findByName(name).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "success", false,
                    "message", "Este juego ya está registrado"));
        }

        try {
            String fileName = saveImage(file);
            Game savedGame = gameRepository.save(new Game(name, fileName));

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "success", true,
                    "message", "Juego creado exitosamente",
                    "data", savedGame));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Error al guardar la imagen del juego"));
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
    public ResponseEntity<Map<String, Object>> updateGame(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile file) {

        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));

        if (!game.getName().equals(name)) {
            if (gameRepository.findByName(name).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "success", false,
                        "message", "Ya existe otro juego con ese nombre"));
            }
        }

        try {
            game.setName(name);
            if (file != null && !file.isEmpty() && !Objects.equals(file.getOriginalFilename(), "default-placeholder")) {
                String fileName = saveImage(file);
                game.setImagePath(fileName);
            }
            Game updatedGame = gameRepository.save(game);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Juego actualizado exitosamente",
                    "data", updatedGame));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Error al procesar la nueva imagen"));
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

    private String saveImage(MultipartFile file) throws IOException {
        String fileName;
        if (file == null || file.isEmpty() || Objects.equals(file.getOriginalFilename(), "default-placeholder")) {
            fileName = "game-default.jpg";
        } else {
            fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
        }
        return fileName;
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "success", false,
                "message", message));
    }
}