package com.proyectoIntermodular.Repository;

import java.util.Optional;
import com.proyectoIntermodular.Models.ModCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModCatRep extends JpaRepository<ModCategories, Long> {
    Optional<ModCategories> findByName(String name);
}
