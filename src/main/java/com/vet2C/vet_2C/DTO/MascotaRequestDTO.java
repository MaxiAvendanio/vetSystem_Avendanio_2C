package com.vet2C.vet_2C.DTO;

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
    @NotBlank(message="El nombre es obligatorio")
    private String nombre;
    @NotBlank(message="La especie es obligatoria")
    private String especie;
    private String raza;
    @PastOrPresent(message = "La fecha de nacimiento debe ser igual o menor a la fecha actual")
    private LocalDate fechaNacimiento;
    @NotNull(message="Debe indicar al dueño")
    @Positive(message="El valor debe ser un positivo")
    private Long idDuenio;
}
