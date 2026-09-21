package com.vet2C.vet_2C.DTO;

import com.vet2C.vet_2C.Entity.EstadoTurno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TurnoResponseDTO {
    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private EstadoTurno estado;
    private String nombreMascota;
    private Long idMascota;
    private Long idVeterinario;
    private String nombreVeterinario;
}
