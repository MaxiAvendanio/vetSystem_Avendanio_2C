package com.vet2C.vet_2C.Repository;

import com.vet2C.vet_2C.Entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    Optional<Medicamento> findByNombreIgnoreCase(String nombre);
}
