package com.vet2C.vet_2C.Service;

import com.vet2C.vet_2C.DTO.MedicamentoRequestDTO;
import com.vet2C.vet_2C.DTO.MedicamentoResponseDTO;
import com.vet2C.vet_2C.Entity.Medicamento;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Mapper.MedicamentoMapper;
import com.vet2C.vet_2C.Repository.MedicamentoRepository;
import com.vet2C.vet_2C.Service.Base.InterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicamentoService implements InterfaceService<MedicamentoRequestDTO, MedicamentoResponseDTO> {
    private final MedicamentoRepository medicamentoRepository;
    private final MedicamentoMapper medicamentoMapper;


    @Override
    public MedicamentoResponseDTO registrarEntidad(MedicamentoRequestDTO medicamentoRequestDTO) {
        Medicamento medicamento = new Medicamento();
        medicamento.setNombre(medicamentoRequestDTO.getNombre());
        medicamento.setPrincipioActivo(medicamentoRequestDTO.getPrincipioActivo());
        medicamento.setStock(medicamentoRequestDTO.getStock());
        medicamento.setPrecioUnitario(medicamentoRequestDTO.getPrecioUnitario());
        return medicamentoMapper.toDto(medicamentoRepository.save(medicamento));
    }

    @Override
    public MedicamentoResponseDTO buscarPorId(Long id) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("No se encontro el medicameto con id: " + id));
        return medicamentoMapper.toDto(medicamento);
    }

    @Override
    public void eliminarEntidad(Long id) {
        if (!medicamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró un medicamento con id: " + id);
        }
        medicamentoRepository.deleteById(id);
    }

    @Override
    public Optional<MedicamentoResponseDTO> buscarEntidadPorString(String s) {
        return medicamentoRepository.findByNombreIgnoreCase(s).map(medicamentoMapper::toDto);
    }

    @Override
    public List<MedicamentoResponseDTO> listarEntidades() {
        return medicamentoRepository.findAll().stream().map(medicamentoMapper::toDto).toList();
    }

    @Override
    public MedicamentoResponseDTO modificarEntidad(Long id, MedicamentoRequestDTO medicamentoRequestDTO) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un medicamento con id: " + id));
        medicamento.setNombre(medicamentoRequestDTO.getNombre());
        medicamento.setPrincipioActivo(medicamentoRequestDTO.getPrincipioActivo());
        medicamento.setStock(medicamentoRequestDTO.getStock());
        medicamento.setPrecioUnitario(medicamentoRequestDTO.getPrecioUnitario());
        return medicamentoMapper.toDto(medicamentoRepository.save(medicamento));
    }
}
