package com.proyectoIntermodular.Controller;

import com.proyectoIntermodular.Models.Mod;

import com.proyectoIntermodular.Repository.ModRep;
import com.proyectoIntermodular.Repository.UserRep;
import com.proyectoIntermodular.Exception.User.UserNotFoundException;
import com.proyectoIntermodular.Exception.Mod.ModNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;


@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/mod")
public class ModController {

  @Autowired
  private ModRep modRepository;

  @Autowired
  private UserRep userRepository;

  @PostMapping("/create")
  public ResponseEntity<Map<String, Object>> createMod(@RequestBody Mod mod) {
    if (mod.getName() == null || mod.getName().isBlank()) {
      return buildErrorResponse("El mod debe tener un nombre");
    }
    if (mod.getDescription() == null || mod.getDescription().isBlank()) {
      return buildErrorResponse("El mod debe tener una descripción");
    }
    if (mod.getVersion() == null || mod.getVersion().isBlank()) {
      return buildErrorResponse("El mod debe tener una versión");
    }
    if (mod.getAuthor() == null || mod.getAuthor().getId() == null) {
      return buildErrorResponse("Es necesario especificar un ID de autor válido");
    }

    if (modRepository.findByName(mod.getName()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
          "success", false,
          "message", "El nombre del mod ya está registrado"));
    }

    Mod savedMod = userRepository.findById(mod.getAuthor().getId()).map(user -> {
      mod.setAuthor(user);
      return modRepository.save(mod);
    }).orElseThrow(() -> new UserNotFoundException(mod.getAuthor().getId()));

    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
        "success", true,
        "message", "Mod creado exitosamente",
        "data", savedMod
    ));
  }

  @GetMapping("/")
  public ResponseEntity<Map<String, Object>> getAllMods() {
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Lista de mods recuperada exitosamente",
        "data", modRepository.findAll()
    ));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getModById(@PathVariable Long id) {
    Mod mod = modRepository.findById(id)
        .orElseThrow(() -> new ModNotFoundException(id));
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Mod encontrado",
        "data", mod
    ));
  }

  @GetMapping("/search")
  public ResponseEntity<Map<String, Object>> searchMods(@RequestParam String name) {
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Resultados de búsqueda",
        "data", modRepository.findByNameContainingIgnoreCase(name)
    ));
  }

  @GetMapping("/author/{id}")
  public ResponseEntity<Map<String, Object>> getModsByUser(@PathVariable Long id) {
    if (!userRepository.existsById(id))
      throw new UserNotFoundException(id);
    
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Mods del usuario recuperados",
        "data", modRepository.findByAuthorId(id)
    ));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String, Object>> updateMod(@RequestBody Mod newMod, @PathVariable Long id) {
    if (newMod.getName() == null || newMod.getName().isBlank()) {
      return buildErrorResponse("El mod debe tener un nombre");
    }
    if (newMod.getDescription() == null || newMod.getDescription().isBlank()) {
      return buildErrorResponse("El mod debe tener una descripción");
    }
    if (newMod.getVersion() == null || newMod.getVersion().isBlank()) {
      return buildErrorResponse("El mod debe tener una versión");
    }

    java.util.Optional<Mod> existingMod = modRepository.findByName(newMod.getName());
    if (existingMod.isPresent() && !existingMod.get().getId().equals(id)) {
       return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
          "success", false,
          "message", "El nombre del mod ya está registrado"));
    }

    if (modRepository.findById(id).isEmpty()) {
       return buildErrorResponse("El mod no existe");
    }

    Mod updatedMod = modRepository.findById(id)
        .map(mod -> {
          mod.setName(newMod.getName());
          mod.setDescription(newMod.getDescription());
          mod.setVersion(newMod.getVersion());
          return modRepository.save(mod);
        }).orElseThrow(() -> new ModNotFoundException(id));
    
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Mod actualizado exitosamente",
        "data", updatedMod
    ));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteMod(@PathVariable Long id) {
    if (!modRepository.existsById(id)) {
      throw new ModNotFoundException(id);
    }
    modRepository.deleteById(id);
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Mod con id " + id + " eliminado exitosamente"
    ));
  }

  private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
        "success", false,
        "message", message));
  }
}