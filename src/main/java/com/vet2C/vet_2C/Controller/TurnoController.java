package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.TurnoRequestDTO;
import com.vet2C.vet_2C.DTO.TurnoResponseDTO;
import com.vet2C.vet_2C.Service.TurnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    @PostMapping
    public ResponseEntity<TurnoResponseDTO> guardarTurno(@Valid @RequestBody TurnoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.registrarEntidad(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.buscarPorId(id));
    }

    @GetMapping
    public List<TurnoResponseDTO> listarTodos() {
        return turnoService.listarEntidades();
    }

    @GetMapping("/mascota/{idMascota}")
    public List<TurnoResponseDTO> listarPorMascota(@PathVariable Long idMascota) {
        return turnoService.listarPorMascota(idMascota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> modificarTurno(@PathVariable Long id, @Valid @RequestBody TurnoRequestDTO dto) {
        return ResponseEntity.ok(turnoService.modificarEntidad(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Long id) {
        turnoService.eliminarEntidad(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/veterinario/{idVeterinario}")
    public List<TurnoResponseDTO> listarPorVeterinarioYFecha(@PathVariable Long idVeterinario, @RequestParam LocalDate fecha) {
        return turnoService.listarPorVeterinarioYFecha(idVeterinario, fecha);
    }

    @GetMapping("/duenio/{nombre}")
    public List<TurnoResponseDTO> listarPorDuenio(@PathVariable String nombre) {
        return turnoService.listarPorDuenio(nombre);
    }
}
