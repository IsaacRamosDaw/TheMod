package com.proyectoIntermodular.Repository;

import com.proyectoIntermodular.Models.Post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRep extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
}
