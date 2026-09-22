package com.vet2C.vet_2C.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaRequestDTO {
    @Schema(description = "Nombre de la mascota", example = "Negro", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Especie de la mascota", example = "Perro", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="La especie es obligatoria")
    private String especie;

    @Schema(description = "Raza de la mascota", example = "Labrador")
    private String raza;

    @Schema(description = "Fecha de nacimiento, no puede ser futura", example = "2023-09-15")
    @PastOrPresent(message = "La fecha de nacimiento debe ser igual o menor a la fecha actual")
    private LocalDate fechaNacimiento;

    @Schema(description = "ID del dueño al que pertenece la mascota", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message="Debe indicar al dueño")
    @Positive(message="El valor debe ser un positivo")
    private Long idDuenio;
}