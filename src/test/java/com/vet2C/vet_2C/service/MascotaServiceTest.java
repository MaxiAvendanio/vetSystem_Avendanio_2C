package com.vet2C.vet_2C.service;

import com.vet2C.vet_2C.DTO.MascotaRequestDTO;
import com.vet2C.vet_2C.DTO.MascotaResponseDTO;
import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Entity.Mascota;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Mapper.MascotaMapper;
import com.vet2C.vet_2C.Repository.DuenioRepository;
import com.vet2C.vet_2C.Repository.MascotaRepository;
import com.vet2C.vet_2C.Service.MascotaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MascotaServiceTest {
    @InjectMocks
    private MascotaService mascotaService;
    @Mock
    private MascotaMapper mascotaMapper;
    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private DuenioRepository duenioRepository;

    //TEST 1: lista vacia
    @Test
    public void listarEntidades_cuando_lista_vacia(){
        //DADO
        when(mascotaRepository.findAll()).thenReturn(List.of());
        //CUANDO
        List<MascotaResponseDTO> resultado = mascotaService.listarEntidades();
        //ENTONCES
        assertThat(resultado).isEmpty();
        verify(mascotaRepository).findAll();
    }

    //TEST 2: buscarPorId exitoso
    @Test
    public void buscarPorId_cuando_sea_exitoso(){
        //DADO
        Mascota mascota = new Mascota();
        mascota.setId(1L);
        mascota.setNombre("Negro");

        MascotaResponseDTO responseDTO = new MascotaResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Negro");

        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(mascotaMapper.toDto(mascota)).thenReturn(responseDTO);

        //CUANDO
        MascotaResponseDTO resultado = mascotaService.buscarPorId(1L);

        //ENTONCES
        assertThat(resultado.getNombre()).isEqualTo("Negro");
        assertThat(resultado.getId()).isEqualTo(1L);
    }
    //TEST 3: buscarPorId cuando no existe
    @Test
    public void buscarPorId_cuando_no_existe(){
        //DADO
        when(mascotaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> mascotaService.buscarPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    //TEST 4: registro mascota exitoso
    @Test
    public void registrarEntidad_exitosa(){
        //DADO
        MascotaRequestDTO mascotaRequestDTO = new MascotaRequestDTO();
        mascotaRequestDTO.setNombre("Negro");
        mascotaRequestDTO.setEspecie("Perro");
        mascotaRequestDTO.setRaza("Labrador");
        mascotaRequestDTO.setFechaNacimiento(LocalDate.of(2023,9,15));
        mascotaRequestDTO.setIdDuenio(2L);

        Duenio duenio = new Duenio();
        duenio.setId(2L);

        Mascota mascotaGuardada = new Mascota();
        mascotaGuardada.setId(1L);
        mascotaGuardada.setNombre("Negro");

        MascotaResponseDTO responseDTO = new MascotaResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Negro");

        when(duenioRepository.findById(2L)).thenReturn(Optional.of(duenio));
        when(mascotaRepository.existsByNombreAndDuenioId("Negro", 2L)).thenReturn(false);
        when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascotaGuardada);
        when(mascotaMapper.toDto(mascotaGuardada)).thenReturn(responseDTO);
        //CUANDO
        MascotaResponseDTO resultado = mascotaService.registrarEntidad(mascotaRequestDTO);
        //ENTONCES
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Negro");
        verify(mascotaRepository).save(any(Mascota.class));
    }

    //Test 5: registrarEntidad cuando el dueño NO existe
    @Test
    public void registrarEntidad_cuando_duenio_no_existe_arroja_excepcion(){
        //DADO
        MascotaRequestDTO dto = new MascotaRequestDTO();
        dto.setIdDuenio(99L);

        when(duenioRepository.findById(99L)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> mascotaService.registrarEntidad(dto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(mascotaRepository, never()).save(any());
    }

    //Test 6: registrarEntidad cuando el nombre ya existe para ese dueño
    @Test
    public void registrarEntidad_cuando_nombre_duplicado_lanza_excepcion(){
        //DADO
        MascotaRequestDTO dto = new MascotaRequestDTO();
        dto.setNombre("Negro");
        dto.setIdDuenio(2L);

        Duenio duenio = new Duenio();
        duenio.setId(2L);

        when(duenioRepository.findById(2L)).thenReturn(Optional.of(duenio));
        when(mascotaRepository.existsByNombreAndDuenioId("Negro", 2L)).thenReturn(true);


        assertThatThrownBy(() -> mascotaService.registrarEntidad(dto))
                .isInstanceOf(DuplicateResourceException.class);

        verify(mascotaRepository, never()).save(any());
    }

    //Test 7: eliminarEntidad cuando NO existe
    @Test
    public void eliminarEntidad_cuando_no_existe_arroja_excepcion(){
        //DADO
        when(mascotaRepository.existsById(99L)).thenReturn(false);


        assertThatThrownBy(() -> mascotaService.eliminarEntidad(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
