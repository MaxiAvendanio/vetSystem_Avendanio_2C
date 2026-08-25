package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Service.DuenioService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Duenio registrarDuenio(@RequestBody Duenio duenio){

        return duenioService.registrarEntidad(duenio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Duenio>> buscarPorId(@PathVariable Long id){
        Optional<Duenio> duenioBuscado = duenioService.buscarPorId(id);

        if(duenioBuscado.isPresent()){
            return ResponseEntity.ok(duenioBuscado);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Duenio> listarTodos(){
        List<Duenio> listaDuenios = duenioService.listarEntidades();
        return listaDuenios;
    }
    @PutMapping
    public Duenio modificarDuenio(@RequestBody Duenio duenio) {
        return duenioService.modificarEntidad(duenio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDuenio(@PathVariable Long id) {
        duenioService.eliminarEntidad(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<Optional<Duenio>> buscarPorNombre(@PathVariable String nombre) {
        Optional<Duenio> duenioBuscado = duenioService.buscarEntidadPorString(nombre);

        if (duenioBuscado.isPresent()) {
            return ResponseEntity.ok(duenioBuscado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
