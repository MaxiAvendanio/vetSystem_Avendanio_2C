package com.vet2C.vet_2C.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarioRequestDTO {
    @Schema(description = "Nombre del veterinario", example = "Ana", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Apellido del veterinario", example = "Gomez", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Schema(description = "Matrícula profesional, formato MP-XXXX", example = "MP-1234", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La matrícula es obligatoria")
    @Pattern(regexp = "MP-\\d+", message = "La matrícula debe tener el formato MP-XXXX")
    private String matricula;

    @Schema(description = "Especialidad del veterinario", example = "Clinica general", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;
}