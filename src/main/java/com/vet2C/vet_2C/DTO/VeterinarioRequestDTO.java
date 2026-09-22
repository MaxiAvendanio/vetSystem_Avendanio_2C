package com.vet2C.vet_2C.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarioRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "La matrícula es obligatoria")
    @Pattern(regexp = "MP-\\d+", message = "La matrícula debe tener el formato MP-XXXX")
    private String matricula;
    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;
}
