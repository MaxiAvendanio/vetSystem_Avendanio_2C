package com.vet2C.vet_2C.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuenioResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String cedula;
    private Integer telefono;
    private String email;
    private List<String> mascotas;
}
