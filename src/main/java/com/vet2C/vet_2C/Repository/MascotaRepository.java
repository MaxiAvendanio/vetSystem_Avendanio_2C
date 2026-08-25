package com.vet2C.vet_2C.Repository;

import com.vet2C.vet_2C.Entity.Duenio;import com.vet2C.vet_2C.Entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MascotaRepository extends JpaRepository<Mascota,Long> {
    Optional<Mascota> findByNombreIgnoreCase(String nombre);
    Optional<Mascota> findByRazaIgnoreCase(String raza);
    List<Mascota> findByDuenioId(Long duenioId);
    boolean existsByNombreAndDuenioId (String nombre, Long duenioId);
    long countByEspecieIgnoreCase(String especie); //probar con ignoreCase y sin
}
