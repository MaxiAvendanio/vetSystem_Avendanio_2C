package com.vet2C.vet_2C.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoResponseDTO {
    private Long id;
    private String nombre;
    private String principioActivo;
    private Integer stock;
    private Double precioUnitario;
}
