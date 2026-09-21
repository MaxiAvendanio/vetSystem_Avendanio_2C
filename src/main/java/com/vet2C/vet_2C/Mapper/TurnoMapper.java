package com.vet2C.vet_2C.Mapper;

import com.vet2C.vet_2C.DTO.TurnoResponseDTO;
import com.vet2C.vet_2C.Entity.Turno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TurnoMapper {
    @Mapping(source = "mascota.id", target = "idMascota")
    @Mapping(source = "mascota.nombre", target = "nombreMascota")
    @Mapping(source = "veterinario.id", target = "idVeterinario")
    @Mapping(source = "veterinario.nombre", target = "nombreVeterinario")
    TurnoResponseDTO toDto(Turno turno);
}
