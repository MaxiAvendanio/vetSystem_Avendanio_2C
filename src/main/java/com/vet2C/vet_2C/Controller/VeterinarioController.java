package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.VeterinarioRequestDTO;
import com.vet2C.vet_2C.DTO.VeterinarioResponseDTO;
import com.vet2C.vet_2C.Service.VeterinarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/veterinario")
@RequiredArgsConstructor
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    @PostMapping
    public ResponseEntity<VeterinarioResponseDTO> registrarVeterinario(@Valid @RequestBody VeterinarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.registrarEntidad(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.buscarPorId(id));
    }

    @GetMapping
    public List<VeterinarioResponseDTO> listarTodos() {
        return veterinarioService.listarEntidades();
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<Optional<VeterinarioResponseDTO>> buscarPorNombre(@PathVariable String nombre) {
        Optional<VeterinarioResponseDTO> encontrado = veterinarioService.buscarEntidadPorString(nombre);
        return encontrado.isPresent() ? ResponseEntity.ok(encontrado) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioResponseDTO> modificarVeterinario(@PathVariable Long id, @Valid @RequestBody VeterinarioRequestDTO dto) {
        return ResponseEntity.ok(veterinarioService.modificarEntidad(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVeterinario(@PathVariable Long id) {
        veterinarioService.eliminarEntidad(id);
        return ResponseEntity.noContent().build();
    }
}
