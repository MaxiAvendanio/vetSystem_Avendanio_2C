package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.DuenioRequestDTO;
import com.vet2C.vet_2C.DTO.DuenioResponseDTO;
import com.vet2C.vet_2C.Service.DuenioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/duenio")
@RequiredArgsConstructor
public class DuenioController {

    private final DuenioService duenioService;

    @PostMapping
    public ResponseEntity<DuenioResponseDTO> registrarDuenio(@Valid  @RequestBody DuenioRequestDTO duenioRequestDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(duenioService.registrarEntidad(duenioRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DuenioResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(duenioService.buscarPorId(id));
    }

    @GetMapping
    public List<DuenioResponseDTO> listarTodos(){
        return duenioService.listarEntidades();
    }
    @PutMapping("/{id}")
    public ResponseEntity<DuenioResponseDTO> modificarDuenio(@PathVariable Long id, @Valid @RequestBody DuenioRequestDTO duenio) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(duenioService.modificarEntidad(id, duenio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDuenio(@PathVariable Long id) {
        duenioService.eliminarEntidad(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<Optional<DuenioResponseDTO>> buscarPorNombre(@PathVariable String nombre) {
        Optional<DuenioResponseDTO> duenioBuscado = duenioService.buscarEntidadPorString(nombre);

        if (duenioBuscado.isPresent()) {
            return ResponseEntity.ok(duenioBuscado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
