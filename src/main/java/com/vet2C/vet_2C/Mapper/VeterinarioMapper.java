package com.vet2C.vet_2C.Mapper;

import com.vet2C.vet_2C.DTO.VeterinarioResponseDTO;
import com.vet2C.vet_2C.Entity.Veterinario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeterinarioMapper {
    VeterinarioResponseDTO toDto(Veterinario veterinario);
}
