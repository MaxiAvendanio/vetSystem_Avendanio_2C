package com.vet2C.vet_2C.controller;

import com.vet2C.vet_2C.Controller.MascotaController;
import com.vet2C.vet_2C.DTO.MascotaRequestDTO;
import com.vet2C.vet_2C.DTO.MascotaResponseDTO;
import com.vet2C.vet_2C.Service.MascotaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MascotaController.class)
public class MascotaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private MascotaService mascotaService;

    private MascotaResponseDTO crearMascotaResponse() {
        MascotaResponseDTO dto = new MascotaResponseDTO();
        dto.setId(1L);
        dto.setNombre("Negro");
        dto.setEspecie("Perro");
        dto.setRaza("Labrador");
        dto.setFechaNacimiento(LocalDate.of(2023, 9, 15));
        dto.setIdDuenio(2L);
        return dto;
    }

    private MascotaRequestDTO crearMascotaRequest() {
        MascotaRequestDTO dto = new MascotaRequestDTO();
        dto.setNombre("Negro");
        dto.setEspecie("Perro");
        dto.setRaza("Labrador");
        dto.setFechaNacimiento(LocalDate.of(2023, 9, 15));
        dto.setIdDuenio(2L);
        return dto;
    }

    @Test
    void getListaVaciaStatus200() throws Exception {
        when(mascotaService.listarEntidades()).thenReturn(List.of());

        mockMvc.perform(get("/api/mascota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getMascotaIdOk() throws Exception {
        when(mascotaService.buscarPorId(1L)).thenReturn(crearMascotaResponse());

        mockMvc.perform(get("/api/mascota/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Negro"));
    }

    @Test
    void postMascotaStatus201() throws Exception {
        when(mascotaService.registrarEntidad(any(MascotaRequestDTO.class))).thenReturn(crearMascotaResponse());

        mockMvc.perform(post("/api/mascota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearMascotaRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Negro"));
    }
}