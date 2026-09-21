package com.vet2C.vet_2C.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuenioRequestDTO {
    private String nombre;
    private String apellido;
    private String cedula;
    private Integer telefono;
    private String email;
}
