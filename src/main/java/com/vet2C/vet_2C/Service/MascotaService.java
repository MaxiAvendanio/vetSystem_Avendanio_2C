package com.vet2C.vet_2C.Service;



import com.vet2C.vet_2C.DTO.MascotaRequestDTO;
import com.vet2C.vet_2C.DTO.MascotaResponseDTO;
import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Entity.Mascota;
import com.vet2C.vet_2C.Exception.CupoExcedidoException;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Mapper.MascotaMapper;
import com.vet2C.vet_2C.Repository.DuenioRepository;
import com.vet2C.vet_2C.Repository.MascotaRepository;
import com.vet2C.vet_2C.Service.Base.InterfaceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class MascotaService implements InterfaceService<MascotaRequestDTO, MascotaResponseDTO> {
    private final MascotaRepository mascotaRepository;
    private final DuenioRepository duenioRepository;
    @Autowired
    private final MascotaMapper mascotaMapper;
    @Override
    public MascotaResponseDTO registrarEntidad(MascotaRequestDTO mascotaRequestDTO) {
        Duenio duenio = duenioRepository.findById(mascotaRequestDTO.getIdDuenio())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el dueño con id: " + mascotaRequestDTO.getIdDuenio()));

        if(mascotaRepository.existsByNombreAndDuenioId(mascotaRequestDTO.getNombre(), mascotaRequestDTO.getIdDuenio())){
            throw new DuplicateResourceException("La mascota ya está registrada: " + mascotaRequestDTO.getNombre());
        }

        if (mascotaRepository.countByDuenioId(mascotaRequestDTO.getIdDuenio()) >= 5) {
            throw new CupoExcedidoException("El dueño ya alcanzó el máximo de 5 mascotas activas");
        }
        Mascota mascota = new Mascota();
        mascota.setNombre(mascotaRequestDTO.getNombre());
        mascota.setEspecie(mascotaRequestDTO.getEspecie());
        mascota.setRaza(mascotaRequestDTO.getRaza());
        mascota.setFechaNacimiento(mascotaRequestDTO.getFechaNacimiento());
        mascota.setDuenio(duenio);
        return mascotaMapper.toDto(mascotaRepository.save(mascota));
    }

    @Override
    public MascotaResponseDTO buscarPorId(Long id) {

        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró a la mascota con id: " + id));

        return mascotaMapper.toDto(mascota);
    }

    @Override
    public MascotaResponseDTO modificarEntidad(Long id,  MascotaRequestDTO mascotaRequestDTO) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No se encontro la mascota con id: " + id));

        mascota.setNombre(mascotaRequestDTO.getNombre());
        mascota.setEspecie(mascotaRequestDTO.getEspecie());
        mascota.setRaza(mascotaRequestDTO.getRaza());
        mascota.setFechaNacimiento(mascotaRequestDTO.getFechaNacimiento());

        return mascotaMapper.toDto(mascotaRepository.save(mascota));
    }

    @Override
    public void eliminarEntidad(Long id) {
        if(!mascotaRepository.existsById(id)){
            throw new ResourceNotFoundException("No se encontro una moscota con id: " + id);
        }
        mascotaRepository.deleteById(id);
    }

    @Override
    public Optional<MascotaResponseDTO> buscarEntidadPorString(String s) {
        return mascotaRepository.findByNombreIgnoreCase(s)
                .map(mascotaMapper::toDto);
    }

    @Override
    public List<MascotaResponseDTO> listarEntidades() {
        return mascotaRepository.findAll()
                .stream()
                .map(mascotaMapper::toDto)
                .toList();
    }

    public boolean existeMascotaPorNombreYDuenio(String nombre, Long duenioId) {
        return mascotaRepository.existsByNombreAndDuenioId(nombre, duenioId);
    }

    public long contarPorEspecie(String especie) {
        return mascotaRepository.countByEspecieIgnoreCase(especie);
    }

    public MascotaResponseDTO buscarPorRaza(String raza) {
        Mascota mascota = mascotaRepository.findByRazaIgnoreCase(raza)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro una mascota con raza: " + raza));
        return mascotaMapper.toDto(mascota);
    }

    public List<MascotaResponseDTO> listarPorDuenio(Long duenioId) {
        return mascotaRepository.findByDuenioId(duenioId)
                .stream()
                .map(mascotaMapper::toDto)
                .toList();
    }
}