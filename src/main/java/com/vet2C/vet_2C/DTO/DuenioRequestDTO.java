package com.vet2C.vet_2C.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuenioRequestDTO {
    @Schema(description = "Nombre del dueño", example = "Maxi", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Apellido del dueño", example = "Avendaño", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="El apellido es obligatorio")
    private String apellido;

    @Schema(description = "Cédula del dueño, entre 7 y 8 caracteres", example = "33895367", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="La cedula es obligatoria")
    @Size(min = 7, max = 8, message = "La cedula tiene que tener entre 7 y 8 caracteres")
    private String cedula;

    @Schema(description = "Teléfono de contacto", example = "3457890", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message="El telefono es obligatorio")
    private Integer telefono;

    @Schema(description = "Email de contacto", example = "mavendano@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;
}