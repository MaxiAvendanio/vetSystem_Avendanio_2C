package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.TurnoRequestDTO;
import com.vet2C.vet_2C.DTO.TurnoResponseDTO;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.HorarioInvalidoException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Service.TurnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/turnos")
@RequiredArgsConstructor
public class TurnoController {
    @Autowired
    private TurnoService turnoService;

    @PostMapping
    public ResponseEntity<?> guardarTurno(@RequestBody TurnoRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.registrarEntidad(dto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch(HorarioInvalidoException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(turnoService.buscarPorId(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
    public ResponseEntity<?> modificarTurno(@PathVariable Long id, @RequestBody TurnoRequestDTO dto) {
        try {
            return ResponseEntity.ok(turnoService.modificarEntidad(id, dto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch(HorarioInvalidoException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) {
        try {
            turnoService.eliminarEntidad(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
