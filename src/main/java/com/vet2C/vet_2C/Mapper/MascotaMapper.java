package com.vet2C.vet_2C.Mapper;

import com.vet2C.vet_2C.DTO.MascotaResponseDTO;
import com.vet2C.vet_2C.Entity.Mascota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MascotaMapper {

    @Mapping(source = "duenio.id", target = "idDuenio")
    @Mapping(source = "duenio.nombre", target = "duenioNombre")
    MascotaResponseDTO toDto(Mascota mascota);

}
