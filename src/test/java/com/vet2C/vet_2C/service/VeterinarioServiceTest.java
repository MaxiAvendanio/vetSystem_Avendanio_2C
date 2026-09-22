package com.vet2C.vet_2C.service;

import com.vet2C.vet_2C.DTO.VeterinarioRequestDTO;
import com.vet2C.vet_2C.DTO.VeterinarioResponseDTO;
import com.vet2C.vet_2C.Entity.Veterinario;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Mapper.VeterinarioMapper;
import com.vet2C.vet_2C.Repository.VeterinarioRepository;
import com.vet2C.vet_2C.Service.VeterinarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeterinarioServiceTest {
    @InjectMocks
    private VeterinarioService veterinarioService;
    @Mock
    private VeterinarioRepository veterinarioRepository;
    @Mock
    private VeterinarioMapper veterinarioMapper;

    //TEST 1: listar veterinarios cuando lista vacia
    @Test
    public void listarEntidades_cuando_lista_vacia(){
        //DADO
        when(veterinarioRepository.findAll()).thenReturn(List.of());
        //CUANDO
        List<VeterinarioResponseDTO> resultado = veterinarioService.listarEntidades();
        //ENTONCES
        assertThat(resultado).isEmpty();
        verify(veterinarioRepository).findAll();
    }
    //Test 2: buscarPorId exitoso
    @Test
    public void buscarPorId_cuando_sea_exitoso(){
        //DADO
        Veterinario veterinario = new Veterinario();
        veterinario.setId(1L);
        veterinario.setNombre("Ana");

        VeterinarioResponseDTO responseDTO = new VeterinarioResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Ana");

        when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinario));
        when(veterinarioMapper.toDto(veterinario)).thenReturn(responseDTO);

        //CUANDO
        VeterinarioResponseDTO resultado = veterinarioService.buscarPorId(1L);

        //ENTONCES
        assertThat(resultado.getNombre()).isEqualTo("Ana");
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    //Test 3: buscarPorId cuando NO existe
    @Test
    public void buscarPorId_cuando_no_existe_lanza_excepcion(){
        //DADO
        when(veterinarioRepository.findById(99L)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> veterinarioService.buscarPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    //Test 4: registrarEntidad exitoso
    @Test
    public void registrarEntidad_cuando_sea_exitoso(){
        //DADO
        VeterinarioRequestDTO dto = new VeterinarioRequestDTO();
        dto.setNombre("Ana");
        dto.setApellido("Gomez");
        dto.setMatricula("MP-1234");
        dto.setEspecialidad("Clinica general");

        Veterinario veterinarioGuardado = new Veterinario();
        veterinarioGuardado.setId(1L);
        veterinarioGuardado.setMatricula("MP-1234");

        VeterinarioResponseDTO responseDTO = new VeterinarioResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setMatricula("MP-1234");

        when(veterinarioRepository.existsByMatricula("MP-1234")).thenReturn(false);
        when(veterinarioRepository.save(any(Veterinario.class))).thenReturn(veterinarioGuardado);
        when(veterinarioMapper.toDto(veterinarioGuardado)).thenReturn(responseDTO);

        //CUANDO
        VeterinarioResponseDTO resultado = veterinarioService.registrarEntidad(dto);

        //ENTONCES
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getMatricula()).isEqualTo("MP-1234");
        verify(veterinarioRepository).save(any(Veterinario.class));
    }

    //Test 5: registrarEntidad con matrícula duplicada
    @Test
    public void registrarEntidad_cuando_matricula_duplicada_lanza_excepcion(){
        //DADO
        VeterinarioRequestDTO dto = new VeterinarioRequestDTO();
        dto.setMatricula("MP-1234");

        when(veterinarioRepository.existsByMatricula("MP-1234")).thenReturn(true);


        assertThatThrownBy(() -> veterinarioService.registrarEntidad(dto))
                .isInstanceOf(DuplicateResourceException.class);

        verify(veterinarioRepository, never()).save(any());
    }

    //Test 6: eliminarEntidad exitoso
    @Test
    public void eliminarEntidad_cuando_existe(){
        //DADO
        when(veterinarioRepository.existsById(1L)).thenReturn(true);

        //CUANDO
        veterinarioService.eliminarEntidad(1L);

        //ENTONCES
        verify(veterinarioRepository).deleteById(1L);
    }

    //Test 7: eliminarEntidad cuando NO existe
    @Test
    public void eliminarEntidad_cuando_no_existe_lanza_excepcion(){
        //DADO
        when(veterinarioRepository.existsById(99L)).thenReturn(false);


        assertThatThrownBy(() -> veterinarioService.eliminarEntidad(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(veterinarioRepository, never()).deleteById(any());
    }

    //Test 8: modificarEntidad exitoso
    @Test
    public void modificarEntidad_cuando_sea_exitoso(){
        //DADO
        VeterinarioRequestDTO dto = new VeterinarioRequestDTO();
        dto.setNombre("Ana");
        dto.setApellido("Gomez");
        dto.setMatricula("MP-1234");
        dto.setEspecialidad("Cirugia");

        Veterinario veterinarioExistente = new Veterinario();
        veterinarioExistente.setId(1L);

        Veterinario veterinarioGuardado = new Veterinario();
        veterinarioGuardado.setId(1L);
        veterinarioGuardado.setEspecialidad("Cirugia");

        VeterinarioResponseDTO responseDTO = new VeterinarioResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setEspecialidad("Cirugia");

        when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinarioExistente));
        when(veterinarioRepository.save(veterinarioExistente)).thenReturn(veterinarioGuardado);
        when(veterinarioMapper.toDto(veterinarioGuardado)).thenReturn(responseDTO);

        //CUANDO
        VeterinarioResponseDTO resultado = veterinarioService.modificarEntidad(1L, dto);

        //ENTONCES
        assertThat(resultado.getEspecialidad()).isEqualTo("Cirugia");
    }

    //Test 9: modificarEntidad cuando NO existe
    @Test
    public void modificarEntidad_cuando_no_existe_lanza_excepcion(){
        //DADO
        VeterinarioRequestDTO dto = new VeterinarioRequestDTO();
        when(veterinarioRepository.findById(99L)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> veterinarioService.modificarEntidad(99L, dto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(veterinarioRepository, never()).save(any());
    }
}
