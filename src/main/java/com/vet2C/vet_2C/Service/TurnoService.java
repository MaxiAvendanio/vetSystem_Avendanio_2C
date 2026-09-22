package com.vet2C.vet_2C.Service;

import com.vet2C.vet_2C.DTO.MedicamentoResponseDTO;
import com.vet2C.vet_2C.DTO.TurnoRequestDTO;
import com.vet2C.vet_2C.DTO.TurnoResponseDTO;
import com.vet2C.vet_2C.Entity.*;
import com.vet2C.vet_2C.Exception.*;
import com.vet2C.vet_2C.Mapper.MedicamentoMapper;
import com.vet2C.vet_2C.Mapper.TurnoMapper;
import com.vet2C.vet_2C.Repository.MascotaRepository;
import com.vet2C.vet_2C.Repository.MedicamentoRepository;
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

    private final TurnoRepository turnoRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final TurnoMapper turnoMapper;
    private final MedicamentoMapper medicamentoMapper;
    private final MedicamentoRepository medicamentoRepository;


    @Override
    public TurnoResponseDTO registrarEntidad(TurnoRequestDTO turnoRequestDTO) {
        validarHorario(turnoRequestDTO.getHora());
        Mascota mascota = mascotaRepository.findById(turnoRequestDTO.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una mascota con id: " + turnoRequestDTO.getIdMascota()));

        Veterinario veterinario = veterinarioRepository.findById(turnoRequestDTO.getIdVeterinario())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un veterinario con id: " + turnoRequestDTO.getIdVeterinario()));

        Optional<Turno> turnoExistente = turnoRepository.findByVeterinarioIdAndFechaAndHora(
                turnoRequestDTO.getIdVeterinario(), turnoRequestDTO.getFecha(), turnoRequestDTO.getHora());

        if (turnoExistente.isPresent()) {
            Turno turno = turnoExistente.get();
            throw new TurnoSuperpuestoException(
                    "El veterinario ya tiene el turno #" + turno.getId() +
                            " programado el " + turno.getFecha() + " a las " + turno.getHora());
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

    public List<MedicamentoResponseDTO> listarMedicamentos(Long turnoId) {
        Turno turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un turno con id: " + turnoId));
        return turno.getMedicamentos().stream().map(medicamentoMapper::toDto).toList();
    }

    @Transactional
    public void asociarMedicamentoATurno(Long turnoId, Long medicamentoId) {
        Turno turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un turno con id: " + turnoId));
        Medicamento medicamento = medicamentoRepository.findById(medicamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un medicamento con id: " + medicamentoId));

        if (medicamento.getStock() <= 0) {
            throw new StockInsuficienteException("No hay stock disponible del medicamento: " + medicamento.getNombre());
        }

        medicamento.setStock(medicamento.getStock() - 1);
        turno.getMedicamentos().add(medicamento);
        medicamentoRepository.save(medicamento);
        turnoRepository.save(turno);
    }

    private static final LocalTime HORA_APERTURA = LocalTime.of(8, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(18, 0);

    private void validarHorario(LocalTime hora) {
        if (hora.isBefore(HORA_APERTURA) || hora.isAfter(HORA_CIERRE.minusMinutes(30))) {
            throw new HorarioInvalidoException(
                    "El horario debe estar entre " + HORA_APERTURA + " y " + HORA_CIERRE);
        }
        if (hora.getMinute() != 0 && hora.getMinute() != 30) {
            throw new HorarioInvalidoException(
                    "Los turnos solo se pueden asignar en punto o y media (ej: 09:00, 09:30)");
        }
    }
}
