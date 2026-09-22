package com.vet2C.vet_2C.Repository;

import com.vet2C.vet_2C.Entity.Mascota;
import com.vet2C.vet_2C.Entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    boolean existsByVeterinarioIdAndFechaAndHora(Long veterinarioId, LocalDate fecha, LocalTime hora);
    Optional<Turno> findByVeterinarioIdAndFechaAndHora(Long veterinarioId, LocalDate fecha, LocalTime hora);
    List<Turno> findByVeterinarioIdAndFecha(Long veterinarioId, LocalDate fecha);
    List<Turno> findByMascotaIdOrderByFechaDesc(Long mascotaId);
    List<Turno> findByMascotaDuenioNombreIgnoreCase(String nombreDuenio);
}
