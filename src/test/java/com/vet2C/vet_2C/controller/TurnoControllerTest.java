package com.vet2C.vet_2C.controller;

import com.vet2C.vet_2C.Controller.TurnoController;
import com.vet2C.vet_2C.DTO.TurnoRequestDTO;
import com.vet2C.vet_2C.DTO.TurnoResponseDTO;
import com.vet2C.vet_2C.Service.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TurnoController.class)
public class TurnoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private TurnoService turnoService;

    private TurnoResponseDTO crearTurnoResponse() {
        TurnoResponseDTO dto = new TurnoResponseDTO();
        dto.setId(1L);
        dto.setMotivo("Control anual");
        return dto;
    }

    private TurnoRequestDTO crearTurnoRequest() {
        TurnoRequestDTO dto = new TurnoRequestDTO();
        dto.setFecha(LocalDate.of(2026, 9, 25));
        dto.setHora(LocalTime.of(9, 0));
        dto.setMotivo("Control anual");
        dto.setIdMascota(1L);
        dto.setIdVeterinario(1L);
        return dto;
    }

    @Test
    void getListaVaciaStatus200() throws Exception {
        when(turnoService.listarEntidades()).thenReturn(List.of());

        mockMvc.perform(get("/api/turnos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getTurnoIdOk() throws Exception {
        when(turnoService.buscarPorId(1L)).thenReturn(crearTurnoResponse());

        mockMvc.perform(get("/api/turnos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.motivo").value("Control anual"));
    }

    @Test
    void postTurnoStatus201() throws Exception {
        when(turnoService.registrarEntidad(any(TurnoRequestDTO.class))).thenReturn(crearTurnoResponse());

        mockMvc.perform(post("/api/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearTurnoRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.motivo").value("Control anual"));
    }
}