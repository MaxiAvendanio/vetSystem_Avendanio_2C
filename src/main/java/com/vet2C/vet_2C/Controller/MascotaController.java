package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.MascotaRequestDTO;
import com.vet2C.vet_2C.DTO.MascotaResponseDTO;
import com.vet2C.vet_2C.Service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascota")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> registrarMascota(@Valid @RequestBody MascotaRequestDTO mascotaRequestDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mascotaService.registrarEntidad(mascotaRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> modificarMascota(@PathVariable Long id, @Valid @RequestBody MascotaRequestDTO mascotaRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mascotaService.modificarEntidad(id, mascotaRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        mascotaService.eliminarEntidad(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(mascotaService.buscarPorId(id));
    }

    @GetMapping
    public List<MascotaResponseDTO> listarTodos(){
        return mascotaService.listarEntidades();
    }

    @GetMapping("/existe")
    public boolean existeMascota(@RequestParam String nombre, @RequestParam Long duenioId) {
        return mascotaService.existeMascotaPorNombreYDuenio(nombre, duenioId);
    }

    @GetMapping("/contar")
    public long contarPorEspecie(@RequestParam String especie) {
        return mascotaService.contarPorEspecie(especie);
    }

    @GetMapping("/raza/{raza}")
    public MascotaResponseDTO buscarPorRaza(@PathVariable String raza) {
        return mascotaService.buscarPorRaza(raza);
    }

    @GetMapping("/duenio/{duenioId}")
    public List<MascotaResponseDTO> listarPorDuenio(@PathVariable Long duenioId) {
        return mascotaService.listarPorDuenio(duenioId);

    }
}
