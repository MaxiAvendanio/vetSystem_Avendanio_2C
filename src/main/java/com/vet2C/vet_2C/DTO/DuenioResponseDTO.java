package com.vet2C.vet_2C.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuenioResponseDTO {
    @Schema(description = "Identificador único del dueño", example = "1")
    private Long id;
    @Schema(description = "Nombre del dueño", example = "Maxi")
    private String nombre;
    @Schema(description = "Apellido del dueño", example = "Avendaño")
    private String apellido;
    @Schema(description = "Cédula del dueño", example = "33895367")
    private String cedula;
    @Schema(description = "Teléfono de contacto", example = "3457890")
    private Integer telefono;
    @Schema(description = "Email de contacto", example = "mavendano@gmail.com")
    private String email;
    @Schema(description = "Nombres de las mascotas asociadas al dueño")
    private List<String> mascotas;
}