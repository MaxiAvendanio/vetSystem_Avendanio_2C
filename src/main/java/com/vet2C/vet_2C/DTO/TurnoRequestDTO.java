package com.vet2C.vet_2C.DTO;

import com.vet2C.vet_2C.Entity.EstadoTurno;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TurnoRequestDTO {
    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "Seleccione una fecha valida")
    private LocalDate fecha;
    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;
    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;
    @NotNull(message = "Debe indicar la mascota del turno")
    @Positive(message = "El valor debe ser un número positivo")
    private Long idMascota;
    @NotNull(message = "Debe indicar el veterinario del turno")
    @Positive(message = "El valor debe ser un número positivo")
    private Long idVeterinario;
}
