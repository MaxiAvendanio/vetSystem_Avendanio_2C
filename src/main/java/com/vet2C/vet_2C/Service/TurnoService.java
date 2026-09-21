package com.vet2C.vet_2C.Service;

import com.vet2C.vet_2C.DTO.TurnoRequestDTO;
import com.vet2C.vet_2C.DTO.TurnoResponseDTO;
import com.vet2C.vet_2C.Entity.EstadoTurno;
import com.vet2C.vet_2C.Entity.Mascota;
import com.vet2C.vet_2C.Entity.Turno;
import com.vet2C.vet_2C.Entity.Veterinario;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Mapper.TurnoMapper;
import com.vet2C.vet_2C.Repository.MascotaRepository;
import com.vet2C.vet_2C.Repository.TurnoRepository;
import com.vet2C.vet_2C.Repository.VeterinarioRepository;
import com.vet2C.vet_2C.Service.Base.InterfaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurnoService implements InterfaceService<TurnoRequestDTO, TurnoResponseDTO>{
    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;
    @Autowired
    private final TurnoMapper turnoMapper;


    @Override
    public TurnoResponseDTO registrarEntidad(TurnoRequestDTO turnoRequestDTO) {
        validarHorario(turnoRequestDTO.getHora());
        Mascota mascota = mascotaRepository.findById(turnoRequestDTO.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una mascota con id: " + turnoRequestDTO.getIdMascota()));

        Veterinario veterinario = veterinarioRepository.findById(turnoRequestDTO.getIdVeterinario())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un veterinario con id: " + turnoRequestDTO.getIdVeterinario()));

        if (turnoRepository.existsByVeterinarioIdAndFechaAndHora(turnoRequestDTO.getIdVeterinario(), turnoRequestDTO.getFecha(), turnoRequestDTO.getHora())) {
            throw new DuplicateResourceException("El veterinario ya tiene un turno asignado en ese horario");
        }

        Turno turno = new Turno();
        turno.setFecha(turnoRequestDTO.getFecha());
        turno.setHora(turnoRequestDTO.getHora());
        turno.setMotivo(turnoRequestDTO.getMotivo());
        turno.setMascota(mascota);
        turno.setVeterinario(veterinario);

        return turnoMapper.toDto(turnoRepository.save(turno));
    }

    @Override
    @Transactional
    public TurnoResponseDTO modificarEntidad(Long id, TurnoRequestDTO turnoRequestDTO) {
        validarHorario(turnoRequestDTO.getHora());
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un turno con id: " + id));

        Mascota mascota = mascotaRepository.findById(turnoRequestDTO.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una mascota con id: " + turnoRequestDTO.getIdMascota()));

        Veterinario veterinario = veterinarioRepository.findById(turnoRequestDTO.getIdVeterinario())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un veterinario con id: " + turnoRequestDTO.getIdVeterinario()));

        turno.setFecha(turnoRequestDTO.getFecha());
        turno.setHora(turnoRequestDTO.getHora());
        turno.setMotivo(turnoRequestDTO.getMotivo());
        turno.setMascota(mascota);
        turno.setVeterinario(veterinario);

        return turnoMapper.toDto(turnoRepository.save(turno));
    }

    @Override
    @Transactional
    public void eliminarEntidad(Long id) {
        if (!turnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró un turno con id: " + id);
        }
        turnoRepository.deleteById(id);
    }

    @Override
    public TurnoResponseDTO buscarPorId(Long id) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un turno con id: " + id));
        return turnoMapper.toDto(turno);
    }


    @Override
    public Optional<TurnoResponseDTO> buscarEntidadPorString(String s) {
        return Optional.empty(); // Turno no tiene un campo de búsqueda por texto natural
    }

    @Override
    public List<TurnoResponseDTO> listarEntidades() {
        return turnoRepository.findAll()
                .stream()
                .map(turnoMapper::toDto)
                .toList();
    }
    public List<TurnoResponseDTO> listarPorVeterinarioYFecha(Long idVeterinario, LocalDate fecha) {
        return turnoRepository.findByVeterinarioIdAndFecha(idVeterinario, fecha)
                .stream()
                .map(turnoMapper::toDto)
                .toList();
    }

    public List<TurnoResponseDTO> listarPorMascota(Long idMascota) {
        return turnoRepository.findByMascotaIdOrderByFechaDesc(idMascota)
                .stream()
                .map(turnoMapper::toDto)
                .toList();
    }

    public List<TurnoResponseDTO> listarPorDuenio(String nombreDuenio) {
        return turnoRepository.findByMascotaDuenioNombreIgnoreCase(nombreDuenio)
                .stream()
                .map(turnoMapper::toDto)
                .toList();
    }


    private static final LocalTime HORA_APERTURA = LocalTime.of(8, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(18, 0);

    private void validarHorario(LocalTime hora) {
        if (hora.isBefore(HORA_APERTURA) || hora.isAfter(HORA_CIERRE.minusMinutes(30))) {
            throw new IllegalArgumentException(
                    "El horario debe estar entre " + HORA_APERTURA + " y " + HORA_CIERRE);
        }
        if (hora.getMinute() != 0 && hora.getMinute() != 30) {
            throw new IllegalArgumentException(
                    "Los turnos solo se pueden asignar en punto o y media (ej: 09:00, 09:30)");
        }
    }
}
