package com.vet2C.vet_2C.Mapper;

import com.vet2C.vet_2C.DTO.MedicamentoResponseDTO;
import com.vet2C.vet_2C.Entity.Medicamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicamentoMapper {
    MedicamentoResponseDTO toDto(Medicamento medicamento);
}
