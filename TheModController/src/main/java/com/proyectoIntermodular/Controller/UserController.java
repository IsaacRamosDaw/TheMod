package com.proyectoIntermodular.Controller;

import com.proyectoIntermodular.Models.User;
import com.proyectoIntermodular.Repository.UserRep;
import com.proyectoIntermodular.Exception.User.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserRep userRepository;

  // private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

  @PostMapping("/register")
  public ResponseEntity<Map<String, Object>> newUser(@RequestBody User user) {

    if (user.getEmail() == null || user.getEmail().isBlank()) {
      return buildErrorResponse("Debe introducir un email");
    }

    if (user.getPassword() == null || user.getPassword().isBlank()) {
      return buildErrorResponse("Debe introducir una contraseña");
    }

    if (user.getName() == null || user.getName().isBlank()) {
      return buildErrorResponse("Debe introducir un nombre de usuario");
    }

    if (userRepository.findByUsername(user.getName()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
          "success", false,
          "message", "El nombre ya está registrado"));
    }

    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
          "success", false,
          "message", "El email ya está registrado"));
    }

    User savedUser = userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
        "success", true,
        "message", "Usuario registrado exitosamente",
        "data", savedUser));
  }

  @GetMapping("/")
  public ResponseEntity<Map<String, Object>> getAllUsers() {
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Lista de usuarios recuperada exitosamente",
        "data", userRepository.findAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Usuario encontrado",
        "data", user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User user) {
    if (user.getEmail() == null || user.getEmail().isBlank()) {
      return buildErrorResponse("Debe introducir un email");
    }
    if (user.getPassword() == null || user.getPassword().isBlank()) {
      return buildErrorResponse("Debe introducir una contraseña");
    }
    if (user.getName() == null || user.getName().isBlank()) {
      return buildErrorResponse("Debe introducir un nombre de usuario");
    }

    if (userRepository.findByUsername(user.getName()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
          "success", false,
          "message", "El nombre ya está registrado"));
    }

    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
          "success", false,
          "message", "El email ya está registrado"));
    }

    if (userRepository.findById(id).isEmpty()) {
      return buildErrorResponse("El usuario no existe");
    }

    User updatedUser = userRepository.findById(id)
        .map(u -> {
          u.setName(user.getName());
          u.setEmail(user.getEmail());
          u.setPassword(user.getPassword());
          return userRepository.save(u);
        })
        .orElseThrow(() -> new UserNotFoundException(id));

    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Usuario actualizado exitosamente",
        "data", updatedUser));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException(id);
    }

    userRepository.deleteById(id);
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Usuario eliminado exitosamente"));
  }

  private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
        "success", false,
        "message", message));
  }
}
