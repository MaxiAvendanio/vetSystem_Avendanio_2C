package com.vet2C.vet_2C.Mapper;

import com.vet2C.vet_2C.DTO.DuenioResponseDTO;
import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Entity.Mascota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DuenioMapper {

    @Mapping(source = "mascotas", target = "mascotas", qualifiedByName = "mascotasANombres")
    DuenioResponseDTO toDto(Duenio duenio);

    @Named("mascotasANombres")
    default List<String> mascotasANombres(List<Mascota> mascotas) {
        if (mascotas == null) return List.of();
        return mascotas.stream().map(Mascota::getNombre).toList();
    }
}
