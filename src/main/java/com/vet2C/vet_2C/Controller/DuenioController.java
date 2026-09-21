package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.DuenioRequestDTO;
import com.vet2C.vet_2C.DTO.DuenioResponseDTO;
import com.vet2C.vet_2C.Exception.DuplicateResourceException;
import com.vet2C.vet_2C.Exception.ResourceNotFoundException;
import com.vet2C.vet_2C.Service.DuenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/duenio")
public class DuenioController {
    @Autowired
    private DuenioService duenioService;

    @PostMapping
    public ResponseEntity<?> registrarDuenio(@RequestBody DuenioRequestDTO duenioRequestDto){
        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(duenioService.registrarEntidad(duenioRequestDto));
        }catch(DuplicateResourceException e){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(duenioService.buscarPorId(id));
        }catch(ResourceNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public List<DuenioResponseDTO> listarTodos(){
        List<DuenioResponseDTO> listaDuenios = duenioService.listarEntidades();
        return listaDuenios;
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarDuenio(@PathVariable Long id, @RequestBody DuenioRequestDTO duenio) {
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(duenioService.modificarEntidad(id, duenio));
        }catch(ResourceNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDuenio(@PathVariable Long id) {
        try{
            duenioService.eliminarEntidad(id);
            return ResponseEntity.noContent().build();
        }catch(ResourceNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
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
