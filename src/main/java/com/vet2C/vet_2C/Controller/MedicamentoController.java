package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.MedicamentoRequestDTO;
import com.vet2C.vet_2C.DTO.MedicamentoResponseDTO;
import com.vet2C.vet_2C.Service.MedicamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> registrar(@Valid @RequestBody MedicamentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoService.registrarEntidad(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentoService.buscarPorId(id));
    }

    @GetMapping
    public List<MedicamentoResponseDTO> listarTodos() {
        return medicamentoService.listarEntidades();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> modificar(@PathVariable Long id, @Valid @RequestBody MedicamentoRequestDTO dto) {
        return ResponseEntity.ok(medicamentoService.modificarEntidad(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicamentoService.eliminarEntidad(id);
        return ResponseEntity.noContent().build();
    }
}