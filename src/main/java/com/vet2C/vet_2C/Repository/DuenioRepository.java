package com.vet2C.vet_2C.Repository;

import com.vet2C.vet_2C.Entity.Duenio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DuenioRepository extends JpaRepository<Duenio,Long> {
    //buscar por nombre HQL
    Optional<Duenio> findByNombre(String nombre);
    Optional<Duenio> findByEmail(String email);
    Optional<Duenio> findByNombreAndApellido(String nombre, String apellido);
}
