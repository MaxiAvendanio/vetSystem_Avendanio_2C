package com.vet2C.vet_2C.Service;

import com.vet2C.vet_2C.DTO.VeterinarioRequestDTO;
import com.vet2C.vet_2C.DTO.VeterinarioResponseDTO;
import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Entity.Veterinario;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Mapper.VeterinarioMapper;
import com.vet2C.vet_2C.Repository.VeterinarioRepository;
import com.vet2C.vet_2C.Service.Base.InterfaceService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor()
public class VeterinarioService implements InterfaceService<VeterinarioRequestDTO, VeterinarioResponseDTO> {
    @Autowired
    private final VeterinarioRepository veterinarioRepository;
    @Autowired
    private final VeterinarioMapper veterinarioMapper;

    @Transactional
    public VeterinarioResponseDTO registrarEntidad(VeterinarioRequestDTO veterinarioRequestDTO) {
        if(veterinarioRepository.existsByMatricula(veterinarioRequestDTO.getMatricula())){
            throw new DuplicateResourceException("Ya existe un veterinario con la matricula: " + veterinarioRequestDTO.getMatricula());
        }

        Veterinario veterinario = new Veterinario();
        veterinario.setNombre(veterinarioRequestDTO.getNombre());
        veterinario.setApellido(veterinarioRequestDTO.getApellido());
        veterinario.setMatricula(veterinarioRequestDTO.getMatricula());
        veterinario.setEspecialidad(veterinarioRequestDTO.getEspecialidad());

        return veterinarioMapper.toDto(veterinarioRepository.save(veterinario));
    }

    @Override
    public VeterinarioResponseDTO buscarPorId(Long id) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un veterinario con id: " + id));

        return veterinarioMapper.toDto(veterinario);
    }

    @Override
    public void eliminarEntidad(Long id) {
        if(!veterinarioRepository.existsById(id)){
            throw new ResourceNotFoundException("No se encontró el veterinario con id: " + id);
        }

        veterinarioRepository.deleteById(id);
    }

    @Override
    public Optional<VeterinarioResponseDTO> buscarEntidadPorString(String s) {
        return veterinarioRepository.findByNombreIgnoreCase(s)
                .map(veterinarioMapper::toDto);
    }

    @Override
    public List<VeterinarioResponseDTO> listarEntidades() {
        return veterinarioRepository.findAll()
                .stream()
                .map(veterinarioMapper::toDto)
                .toList();
    }

    @Override
    public VeterinarioResponseDTO modificarEntidad(Long id, VeterinarioRequestDTO veterinarioRequestDTO) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un veterinario con id: " + id));

        veterinario.setNombre(veterinarioRequestDTO.getNombre());
        veterinario.setApellido(veterinarioRequestDTO.getApellido());
        veterinario.setMatricula(veterinarioRequestDTO.getMatricula());
        veterinario.setEspecialidad(veterinarioRequestDTO.getEspecialidad());

        return veterinarioMapper.toDto(veterinarioRepository.save(veterinario));
    }


}
