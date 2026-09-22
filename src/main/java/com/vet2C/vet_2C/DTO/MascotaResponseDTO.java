package com.vet2C.vet_2C.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaResponseDTO {
    @Schema(description = "Identificador único de la mascota", example = "1")
    private Long id;
    @Schema(description = "Nombre de la mascota", example = "Negro")
    private String nombre;
    @Schema(description = "Especie de la mascota", example = "Perro")
    private String especie;
    @Schema(description = "Raza de la mascota", example = "Labrador")
    private String raza;
    @Schema(description = "Fecha de nacimiento", example = "2023-09-15")
    private LocalDate fechaNacimiento;
    @Schema(description = "ID del dueño de la mascota", example = "2")
    private Long idDuenio;
    @Schema(description = "Nombre del dueño de la mascota", example = "Maxi")
    private String duenioNombre;
}