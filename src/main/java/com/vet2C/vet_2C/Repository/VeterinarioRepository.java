package com.vet2C.vet_2C.Repository;

import com.vet2C.vet_2C.Entity.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeterinarioRepository extends JpaRepository<Veterinario,Long> {
    Optional<Veterinario> findById(Long id);
    Optional<Veterinario> findByNombreIgnoreCase(String nombre);
    boolean existsByMatricula(String matricula);
}
