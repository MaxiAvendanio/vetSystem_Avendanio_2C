package com.vet2C.vet_2C.controller;

import com.vet2C.vet_2C.Controller.VeterinarioController;
import com.vet2C.vet_2C.DTO.VeterinarioRequestDTO;
import com.vet2C.vet_2C.DTO.VeterinarioResponseDTO;
import com.vet2C.vet_2C.Service.VeterinarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VeterinarioController.class)
public class VeterinarioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private VeterinarioService veterinarioService;

    private VeterinarioResponseDTO crearVeterinarioResponse() {
        VeterinarioResponseDTO dto = new VeterinarioResponseDTO();
        dto.setId(1L);
        dto.setNombre("Ana");
        dto.setApellido("Gomez");
        dto.setMatricula("MP-1234");
        dto.setEspecialidad("Clinica general");
        return dto;
    }

    private VeterinarioRequestDTO crearVeterinarioRequest() {
        VeterinarioRequestDTO dto = new VeterinarioRequestDTO();
        dto.setNombre("Ana");
        dto.setApellido("Gomez");
        dto.setMatricula("MP-1234");
        dto.setEspecialidad("Clinica general");
        return dto;
    }

    @Test
    void getListaVaciaStatus200() throws Exception {
        when(veterinarioService.listarEntidades()).thenReturn(List.of());

        mockMvc.perform(get("/api/veterinario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getVeterinarioIdOk() throws Exception {
        when(veterinarioService.buscarPorId(1L)).thenReturn(crearVeterinarioResponse());

        mockMvc.perform(get("/api/veterinario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matricula").value("MP-1234"));
    }

    @Test
    void postVeterinarioStatus201() throws Exception {
        when(veterinarioService.registrarEntidad(any(VeterinarioRequestDTO.class))).thenReturn(crearVeterinarioResponse());

        mockMvc.perform(post("/api/veterinario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearVeterinarioRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }
}