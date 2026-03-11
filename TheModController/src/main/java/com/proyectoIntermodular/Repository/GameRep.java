package com.proyectoIntermodular.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectoIntermodular.Models.Game;

@Repository
public interface GameRep extends JpaRepository<Game, Long> {
  Optional<Game> findByName(String name);
}
