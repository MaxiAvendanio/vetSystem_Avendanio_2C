package com.vet2C.vet_2C.Repository;

import com.vet2C.vet_2C.Entity.Duenio;import com.vet2C.vet_2C.Entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MascotaRepository extends JpaRepository<Mascota,Long> {
    Optional<Mascota> findByNombre(String nombre);
}
