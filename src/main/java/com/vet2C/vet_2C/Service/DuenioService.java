package com.vet2C.vet_2C.Service;

import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Repository.DuenioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DuenioService {

    private final DuenioRepository duenioRepository;


    public Duenio registrarDuenio(Duenio duenio){
        return duenioRepository.save(duenio);
    }

    public Optional<Duenio> buscarPorId(Long id){
        return duenioRepository.findById(id);
    }

    public List<Duenio> listarTodos(){
        return duenioRepository.findAll();
    }

    public void eliminarDuenio(Long id){
        duenioRepository.deleteById(id);
    }

    public Optional<Duenio> buscarPornombre(String nombre){
        return duenioRepository.findByNombre(nombre);
    }
}