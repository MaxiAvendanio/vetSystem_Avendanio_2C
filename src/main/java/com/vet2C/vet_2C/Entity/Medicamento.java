package com.vet2C.vet_2C.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name="Medicamentos")
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String principioActivo;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private double precioUnitario;

    @ManyToMany(mappedBy = "medicamentos")
    private List<Turno> turnos;
}
