package com.vet2C.vet_2C.Service;

import com.vet2C.vet_2C.DTO.TurnoRequestDTO;
import com.vet2C.vet_2C.DTO.TurnoResponseDTO;
import com.vet2C.vet_2C.Entity.Mascota;
import com.vet2C.vet_2C.Entity.Turno;
import com.vet2C.vet_2C.Entity.Veterinario;
import com.vet2C.vet_2C.Exception.HorarioInvalidoException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Exception.TurnoSuperpuestoException;
import com.vet2C.vet_2C.Mapper.TurnoMapper;
import com.vet2C.vet_2C.Repository.MascotaRepository;
import com.vet2C.vet_2C.Repository.TurnoRepository;
import com.vet2C.vet_2C.Repository.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TurnoServiceTest {

    @InjectMocks
    private TurnoService turnoService;
    @Mock
    private TurnoRepository turnoRepository;
    @Mock
    private MascotaRepository mascotaRepository;
    @Mock
    private VeterinarioRepository veterinarioRepository;
    @Mock
    private TurnoMapper turnoMapper;

    //Test 1: registrarEntidad exitoso
    @Test
    public void registrarEntidad_cuando_sea_exitoso(){
        //DADO
        TurnoRequestDTO dto = new TurnoRequestDTO();
        dto.setFecha(LocalDate.of(2026, 9, 25));
        dto.setHora(LocalTime.of(9, 0));
        dto.setIdMascota(1L);
        dto.setIdVeterinario(1L);

        Mascota mascota = new Mascota();
        mascota.setId(1L);
        Veterinario veterinario = new Veterinario();
        veterinario.setId(1L);

        Turno turnoGuardado = new Turno();
        turnoGuardado.setId(1L);

        TurnoResponseDTO responseDTO = new TurnoResponseDTO();
        responseDTO.setId(1L);

        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinario));
        when(turnoRepository.existsByVeterinarioIdAndFechaAndHora(1L, dto.getFecha(), dto.getHora())).thenReturn(false);
        when(turnoRepository.save(any(Turno.class))).thenReturn(turnoGuardado);
        when(turnoMapper.toDto(turnoGuardado)).thenReturn(responseDTO);

        //CUANDO
        TurnoResponseDTO resultado = turnoService.registrarEntidad(dto);

        //ENTONCES
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(turnoRepository).save(any(Turno.class));
    }

    //Test 2: registrarEntidad con turno superpuesto -> excepción
    @Test
    public void registrarEntidad_cuando_turno_superpuesto_lanza_excepcion(){
        //DADO
        TurnoRequestDTO dto = new TurnoRequestDTO();
        dto.setFecha(LocalDate.of(2026, 9, 25));
        dto.setHora(LocalTime.of(9, 0));
        dto.setIdMascota(1L);
        dto.setIdVeterinario(1L);

        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(new Mascota()));
        when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(new Veterinario()));
        when(turnoRepository.existsByVeterinarioIdAndFechaAndHora(1L, dto.getFecha(), dto.getHora())).thenReturn(true);

        //CUANDO / ENTONCES
        assertThatThrownBy(() -> turnoService.registrarEntidad(dto))
                .isInstanceOf(TurnoSuperpuestoException.class);

        verify(turnoRepository, never()).save(any());
    }

    //Test 3: registrarEntidad con horario fuera de rango/grilla -> excepción
    @Test
    public void registrarEntidad_cuando_horario_invalido_lanza_excepcion(){
        //DADO
        TurnoRequestDTO dto = new TurnoRequestDTO();
        dto.setHora(LocalTime.of(19, 0));

        //CUANDO / ENTONCES
        assertThatThrownBy(() -> turnoService.registrarEntidad(dto))
                .isInstanceOf(HorarioInvalidoException.class);

        verify(turnoRepository, never()).save(any());
    }

    //Test 4: buscarPorId cuando NO existe -> excepción
    @Test
    public void buscarPorId_cuando_no_existe_lanza_excepcion(){
        //DADO
        when(turnoRepository.findById(99L)).thenReturn(Optional.empty());

        //CUANDO / ENTONCES
        assertThatThrownBy(() -> turnoService.buscarPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    //Test 5: eliminarEntidad exitoso
    @Test
    public void eliminarEntidad_cuando_existe(){
        //DADO
        when(turnoRepository.existsById(1L)).thenReturn(true);

        //CUANDO
        turnoService.eliminarEntidad(1L);

        //ENTONCES
        verify(turnoRepository).deleteById(1L);
    }
}