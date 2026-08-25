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

        return mascotaService.registrarEntidad(mascota);
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
        List<Mascota> listaMascota = mascotaService.listarEntidades();
        return listaMascota;
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
    public ResponseEntity<Optional<Mascota>> buscarPorRaza(@PathVariable String raza) {
        Optional<Mascota> mascota = mascotaService.buscarPorRaza(raza);

        if (mascota.isPresent()) {
            return ResponseEntity.ok(mascota);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/duenio/{duenioId}")
    public List<Mascota> listarPorDuenio(@PathVariable Long duenioId) {
        return mascotaService.listarPorDuenio(duenioId);
    }
}
