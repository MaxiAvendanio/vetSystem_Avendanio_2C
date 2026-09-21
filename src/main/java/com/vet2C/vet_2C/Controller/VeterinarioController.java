package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.VeterinarioRequestDTO;
import com.vet2C.vet_2C.DTO.VeterinarioResponseDTO;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/veterinario")
public class VeterinarioController {
    @Autowired
    private  VeterinarioService veterinarioService;

    @PostMapping
    public ResponseEntity<?> registrarVeterinario(@RequestBody VeterinarioRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.registrarEntidad(dto));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(veterinarioService.buscarPorId(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
    public ResponseEntity<?> modificarVeterinario(@PathVariable Long id, @RequestBody VeterinarioRequestDTO dto) {
        try {
            return ResponseEntity.ok(veterinarioService.modificarEntidad(id, dto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVeterinario(@PathVariable Long id) {
        try {
            veterinarioService.eliminarEntidad(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
