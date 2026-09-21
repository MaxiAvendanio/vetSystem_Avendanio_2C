package com.vet2C.vet_2C.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaRequestDTO {
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private Long idDuenio;
}
