package com.vet2C.vet_2C.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarioResponseDTO {
    @Schema(description = "Identificador único del veterinario", example = "1")
    private Long id;
    @Schema(description = "Nombre del veterinario", example = "Ana")
    private String nombre;
    @Schema(description = "Apellido del veterinario", example = "Gomez")
    private String apellido;
    @Schema(description = "Matrícula profesional", example = "MP-1234")
    private String matricula;
    @Schema(description = "Especialidad del veterinario", example = "Clinica general")
    private String especialidad;
}