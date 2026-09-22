package com.vet2C.vet_2C.controller;

import com.vet2C.vet_2C.Controller.DuenioController;
import com.vet2C.vet_2C.DTO.DuenioRequestDTO;
import com.vet2C.vet_2C.DTO.DuenioResponseDTO;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Service.DuenioService;
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

@WebMvcTest(DuenioController.class)
public class DuenioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private DuenioService duenioService;

    //crear el objeto de respuesta
    private DuenioResponseDTO crearDuenioResponse() {
        DuenioResponseDTO duenioResponsetDTO = new DuenioResponseDTO();
        duenioResponsetDTO.setId(1L);
        duenioResponsetDTO.setNombre("Juan");
        duenioResponsetDTO.setApellido("Perez");
        duenioResponsetDTO.setCedula("32123456");
        duenioResponsetDTO.setEmail("jperez@gmail.com");
        duenioResponsetDTO.setTelefono(11234567);
        return duenioResponsetDTO;
    }
    //crear el objeto de request (lo que manda el cliente)
    private DuenioRequestDTO crearDuenioRequest() {
        DuenioRequestDTO duenioRequestDTO = new DuenioRequestDTO();
        duenioRequestDTO.setNombre("Juan");
        duenioRequestDTO.setApellido("Perez");
        duenioRequestDTO.setCedula("32123456");
        duenioRequestDTO.setEmail("jperez@gmail.com");
        duenioRequestDTO.setTelefono(11234567);
        return duenioRequestDTO;
    }

        @Test
        void getListaVaciaStatus200() throws Exception{
            //DADO
            when(duenioService.listarEntidades()).thenReturn(List.of());
            //CUANDO + ENTONCES
            mockMvc.perform(get("/api/duenio"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(0));
        }

        @Test
        public void getDunioIdOk() throws Exception{
        when(duenioService.buscarPorId(1L)).thenReturn(crearDuenioResponse());
        //CUANDO + ENTONCES
            mockMvc.perform(get("/api/duenio/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.nombre").value("Juan"));
        }

        @Test
        public void postDuenioDesdeCuerpoStatus200() throws Exception{
            //DADO
            DuenioRequestDTO requestBody = crearDuenioRequest();
            DuenioResponseDTO responseEsperado = crearDuenioResponse();

            when(duenioService.registrarEntidad(any(DuenioRequestDTO.class))).thenReturn(responseEsperado);

            //CUANDO + ENTONCES
            mockMvc.perform(post("/api/duenio")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestBody)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.nombre").value("Juan"));
        }

    @Test
    void postDuenioBodyInvalido_status400() throws Exception {
        //DADO: request sin nombre, sin cédula, con email mal formado
        DuenioRequestDTO requestInvalido = new DuenioRequestDTO();
        requestInvalido.setEmail("no-es-un-email");

        //CUANDO + ENTONCES
        mockMvc.perform(post("/api/duenio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getDuenioIdInexistente_status404() throws Exception {
        //DADO
        when(duenioService.buscarPorId(99L))
                .thenThrow(new ResourceNotFoundException("No se encontró un dueño con id: 99"));

        //CUANDO + ENTONCES
        mockMvc.perform(get("/api/duenio/99"))
                .andExpect(status().isNotFound());
    }

}
