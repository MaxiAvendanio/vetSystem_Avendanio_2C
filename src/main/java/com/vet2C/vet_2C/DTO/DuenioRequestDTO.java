package com.vet2C.vet_2C.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuenioRequestDTO {
    @NotBlank(message="El nombre es obligatorio")
    private String nombre;
    @NotBlank(message="El apellido es obligatorio")
    private String apellido;
    @NotBlank(message="La cedula es obligatoria")
    @Size(min = 7, max = 8, message = "La cedula tiene que tener entre 7 y 8 caracteres")
    private String cedula;
    @NotNull(message="El telefono es obligatorio")
    private Integer telefono;
    @NotBlank(message="El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;
}
