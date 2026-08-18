package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Entity.Mascota;
import com.vet2C.vet_2C.Service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Mascota registrarMascota(@RequestBody Mascota mascota){
        return mascotaService.registrarMascota(mascota);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Mascota>> buscarPorId(Long id){
        Optional<Mascota> mascotaBuscada = mascotaService.buscarPorId(id);

        if(mascotaBuscada.isPresent()){
            return ResponseEntity.ok(mascotaBuscada);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Mascota> listarTodos(){
        List<Mascota> listaMascota = mascotaService.listarTodos();
        return listaMascota;
    }
}
