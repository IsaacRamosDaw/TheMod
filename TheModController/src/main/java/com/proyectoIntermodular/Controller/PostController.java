package com.proyectoIntermodular.Controller;

import com.proyectoIntermodular.Models.Post;
import com.proyectoIntermodular.Models.User;
import com.proyectoIntermodular.Repository.PostRep;
import com.proyectoIntermodular.Repository.UserRep;
import com.proyectoIntermodular.Exception.post.PostNotFoundException;
import com.proyectoIntermodular.Exception.User.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostRep postRepository;

    @Autowired
    private UserRep userRepository;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> newPost(@RequestBody Post post) {
        if (post.getTitle() == null || post.getTitle().isBlank()) {
            return buildErrorResponse("El título del post es obligatorio");
        }
        if (post.getContent() == null || post.getContent().isBlank()) {
            return buildErrorResponse("El contenido del post es obligatorio");
        }
        
        // Ensure author exists if passed by ID in some way, though usually @RequestBody Post mapping handles User
        if (post.getAuthor() == null || post.getAuthor().getId() == null) {
            return buildErrorResponse("El autor del post es obligatorio");
        }

        User author = userRepository.findById(post.getAuthor().getId())
                .orElseThrow(() -> new UserNotFoundException(post.getAuthor().getId()));
        
        post.setAuthor(author);
        Post savedPost = postRepository.save(post);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "success", true,
                "message", "Post creado exitosamente",
                "data", savedPost));
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Lista de posts recuperada exitosamente",
                "data", posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPostById(@PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Post encontrado",
                "data", post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable Long id, @RequestBody Post post) {
        if (post.getTitle() == null || post.getTitle().isBlank()) {
            return buildErrorResponse("El título del post es obligatorio");
        }
        if (post.getContent() == null || post.getContent().isBlank()) {
            return buildErrorResponse("El contenido del post es obligatorio");
        }

        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        
        Post updatedPost = postRepository.save(existingPost);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Post actualizado exitosamente",
                "data", updatedPost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }
        postRepository.deleteById(id);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Post eliminado exitosamente"));
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "success", false,
                "message", message));
    }
}
