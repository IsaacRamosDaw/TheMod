package com.proyectoIntermodular.Repository;

import java.util.Optional;
import com.proyectoIntermodular.Models.GameCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCatRep extends JpaRepository<GameCategories, Long> {
    Optional<GameCategories> findByName(String name);
}
