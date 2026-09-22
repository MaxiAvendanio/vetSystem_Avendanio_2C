package com.vet2C.vet_2C.service;

import com.vet2C.vet_2C.DTO.DuenioRequestDTO;
import com.vet2C.vet_2C.DTO.DuenioResponseDTO;
import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Mapper.DuenioMapper;
import com.vet2C.vet_2C.Repository.DuenioRepository;
import com.vet2C.vet_2C.Service.DuenioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DuenioServiceTest {
    @InjectMocks
    private DuenioService duenioService;
    @Mock
    private DuenioMapper duenioMapper;
    @Mock
    private DuenioRepository duenioRepository;

    //Test 1: Obtener todos los duenios
    @Test
    public void getAllDuenioListaVacia(){
        //DADO
        when(duenioRepository.findAll()).thenReturn(List.of());
        //CUANDO
        List<DuenioResponseDTO> resultado = duenioService.listarEntidades();
        //ENTONCES
        assertThat(resultado.isEmpty());
        verify(duenioRepository).findAll();
    }
    //Test 2: getDuenioId exitoso
    @Test
    public void getDuenioById_cuando_sea_exitosoDTO(){
        //DADO
        Duenio duenio = new Duenio();
        duenio.setId(1L);
        duenio.setNombre("Maxi");
        duenio.setApellido("Avendaño");
        duenio.setCedula("33895367");
        duenio.setEmail("maxi@gmail.com");
        duenio.setTelefono(1234567);

        DuenioResponseDTO duenioResponseDTO= new DuenioResponseDTO();
        duenioResponseDTO.setId(duenio.getId());
        duenioResponseDTO.setNombre(duenio.getNombre());
        //CUANDO
        when(duenioRepository.findById(1L)).thenReturn(Optional.of(duenio));
        when(duenioMapper.toDto(duenio)).thenReturn(duenioResponseDTO);
        //ENTONCES
        DuenioResponseDTO resultado = duenioService.buscarPorId(1L);
        assertThat(resultado.getNombre()).isEqualTo("Maxi");
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    //Test 3: registrarEntidad exitosa
    @Test
    public void registrarEntidad_cuando_sea_exitosa(){
        //DADO
        DuenioRequestDTO duenioRequestDTO = new DuenioRequestDTO();
        duenioRequestDTO.setNombre("Maxi");
        duenioRequestDTO.setApellido("Avendaño");
        duenioRequestDTO.setCedula("33895367");
        duenioRequestDTO.setTelefono(1234567);
        duenioRequestDTO.setEmail("maxi@gmail.com");

        Duenio duenioGuardado = new Duenio();
        duenioGuardado.setId(1L);
        duenioGuardado.setNombre("Maxi");

        DuenioResponseDTO responseDTO = new DuenioResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Maxi");

        when(duenioRepository.existsByCedula("33895367")).thenReturn(false);
        when(duenioRepository.save(any(Duenio.class))).thenReturn(duenioGuardado);
        when(duenioMapper.toDto(duenioGuardado)).thenReturn(responseDTO);

        //CUANDO
        DuenioResponseDTO resultado = duenioService.registrarEntidad(duenioRequestDTO);
        //ENTONCES
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Maxi");
        verify(duenioRepository).save(any(Duenio.class));
    }

    //Test 4: registrarEntidad con cedula duplicada
    @Test
    public void registrarEntidad_cuando_cedula_duplicada_arroja_excepcion(){
        //DADO
        DuenioRequestDTO duenioRequestDTO = new DuenioRequestDTO();
        duenioRequestDTO.setCedula("33895367");

        when(duenioRepository.existsByCedula("33895367")).thenReturn(true);

        assertThatThrownBy(() -> duenioService.registrarEntidad(duenioRequestDTO))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Ya existe un dueño con la cedula: 33895367");

        //verifico que cuando se lanza le exception de arriba, el codigo corta y no se guarda nada
        verify(duenioRepository, never()).save(any());
    }

    //Test 5: buscarPorId cuando NO existe
    @Test
    public void getDuenioById_cuando_no_existe_arroja_excepcion(){
        //DADO
        when(duenioRepository.findById(99L)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> duenioService.buscarPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    //Test 6: eliminarEntidad exitoso
    @Test
    public void eliminarEntidad_cuando_existe(){
        //DADO
        when(duenioRepository.existsById(1L)).thenReturn(true);

        //CUANDO
        duenioService.eliminarEntidad(1L);

        //ENTONCES
        verify(duenioRepository).deleteById(1L);
    }

    //Test 7: eliminarEntidad cuando NO existe -> excepción
    @Test
    public void eliminarEntidad_cuando_no_existe_lanza_excepcion(){
        //DADO
        when(duenioRepository.existsById(99L)).thenReturn(false);

        //CUANDO / ENTONCES
        assertThatThrownBy(() -> duenioService.eliminarEntidad(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(duenioRepository, never()).deleteById(any());
    }

}
