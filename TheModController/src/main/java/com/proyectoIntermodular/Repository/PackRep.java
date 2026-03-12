package com.proyectoIntermodular.Repository;

import java.util.Optional;
import com.proyectoIntermodular.Models.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackRep extends JpaRepository<Pack, Long> {
    Optional<Pack> findByName(String name);
}
