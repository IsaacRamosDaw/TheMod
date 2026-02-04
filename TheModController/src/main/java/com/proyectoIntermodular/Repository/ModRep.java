package com.proyectoIntermodular.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectoIntermodular.Models.Mod;
import com.proyectoIntermodular.Models.User;

import java.util.List;
import java.util.Optional;

public interface ModRep extends JpaRepository<Mod, Long> {
    List<Mod> findByAuthor(User author);
    List<Mod> findByAuthorId(Long userId);
    List<Mod> findByNameContainingIgnoreCase(String name);
    Optional<Mod> findByName(String name);
}
