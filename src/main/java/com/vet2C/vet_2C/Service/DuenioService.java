package com.vet2C.vet_2C.Service;

import com.vet2C.vet_2C.DTO.DuenioRequestDTO;
import com.vet2C.vet_2C.DTO.DuenioResponseDTO;
import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Mapper.DuenioMapper;
import com.vet2C.vet_2C.Repository.DuenioRepository;
import com.vet2C.vet_2C.Service.Base.InterfaceService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor()
public class DuenioService implements InterfaceService<DuenioRequestDTO, DuenioResponseDTO> {
    @Autowired
    private final DuenioRepository duenioRepository;
    @Autowired
    private final DuenioMapper duenioMapper;

    @Transactional
    public DuenioResponseDTO registrarEntidad(DuenioRequestDTO duenioRequestDto) {
        if(duenioRepository.existsByCedula(duenioRequestDto.getCedula())){
            throw new DuplicateResourceException("Ya existe un dueño con la cedula: "+duenioRequestDto.getCedula());
        }
        Duenio duenio = new Duenio();
        duenio.setNombre(duenioRequestDto.getNombre());
        duenio.setApellido(duenioRequestDto.getApellido());
        duenio.setEmail(duenioRequestDto.getEmail());
        duenio.setCedula(duenioRequestDto.getCedula());
        duenio.setTelefono(duenioRequestDto.getTelefono());
        return duenioMapper.toDto(duenioRepository.save(duenio));
    }

    @Override
    public DuenioResponseDTO buscarPorId(Long id) {
        Duenio duenio = duenioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un dueño con id: " + id));

        return duenioMapper.toDto(duenio);
    }

    @Override
    public void eliminarEntidad(Long id) {
        if(!duenioRepository.existsById(id)){
            throw new ResourceNotFoundException("No se encontró dueno con id: " + id);
        }

        duenioRepository.deleteById(id);
    }

    @Override
    public Optional<DuenioResponseDTO> buscarEntidadPorString(String s) {
        return duenioRepository.findByNombreIgnoreCase(s)
                .map(duenioMapper::toDto);
    }

    @Override
    public List<DuenioResponseDTO> listarEntidades() {
        return duenioRepository.findAll()
                .stream()
                .map(duenioMapper::toDto)
                .toList();
    }

    @Override
    public DuenioResponseDTO modificarEntidad(Long id, DuenioRequestDTO duenioRequestDTO) {

        Duenio duenio = duenioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un dueño con id: " + id));

        duenio.setNombre(duenioRequestDTO.getNombre());
        duenio.setApellido(duenioRequestDTO.getApellido());
        duenio.setCedula(duenioRequestDTO.getCedula());
        duenio.setTelefono(duenioRequestDTO.getTelefono());
        duenio.setEmail(duenioRequestDTO.getEmail());

        return duenioMapper.toDto(duenioRepository.save(duenio));
    }

}