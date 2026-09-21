package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.MascotaRequestDTO;
import com.vet2C.vet_2C.DTO.MascotaResponseDTO;
import com.vet2C.vet_2C.Entity.Mascota;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mascota")
public class MascotaController {
    @Autowired
    private MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<?> registrarMascota(@RequestBody MascotaRequestDTO mascotaRequestDto){
        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(mascotaService.registrarEntidad(mascotaRequestDto));
        }catch(DuplicateResourceException e){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> modificarMascota(@PathVariable Long id, @RequestBody MascotaRequestDTO mascotaRequestDto) {
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mascotaService.modificarEntidad(id, mascotaRequestDto));
        }catch(ResourceNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMascota(@PathVariable Long id) {
        try{
            mascotaService.eliminarEntidad(id);
            return ResponseEntity.noContent().build();
        }catch(ResourceNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(mascotaService.buscarPorId(id));
        }catch(ResourceNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
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
