package com.vet2C.vet_2C.Service;


import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Entity.Mascota;
import com.vet2C.vet_2C.Repository.MascotaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class MascotaService {
    private final MascotaRepository mascotaRepository;

    public Mascota registrarMascota(Mascota mascota){
        return mascotaRepository.save(mascota);
    }

    public Optional<Mascota> buscarPorId(Long id){
        return mascotaRepository.findById(id);
    }

    public List<Mascota> listarTodos(){
        return mascotaRepository.findAll();
    }

    public void eliminarMascota(Long id){
        mascotaRepository.deleteById(id);
    }
    public Optional<Mascota> buscarPornombre(String nombre){
        return mascotaRepository.findByNombre(nombre);
    }
}