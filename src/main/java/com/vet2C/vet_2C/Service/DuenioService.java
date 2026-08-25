package com.vet2C.vet_2C.Service;

import com.vet2C.vet_2C.Entity.Duenio;
import com.vet2C.vet_2C.Repository.DuenioRepository;
import com.vet2C.vet_2C.Service.Base.InterfaceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DuenioService implements InterfaceService<Duenio> {

    private final DuenioRepository duenioRepository;
    @Override
    public Duenio registrarEntidad(Duenio duenio) {
        return duenioRepository.save(duenio);
    }

    @Override
    public Optional<Duenio> buscarPorId(Long id) {
        return duenioRepository.findById(id);
    }

    @Override
    public Duenio modificarEntidad(Duenio duenio) {
        return duenioRepository.save(duenio);
    }

    @Override
    public void eliminarEntidad(Long id) {
        Optional<Duenio> duenio = duenioRepository.findById(id);
        if(duenio.isPresent())
        {
            duenioRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Duenio> buscarEntidadPorString(String s) {
        return duenioRepository.findByNombreIgnoreCase(s);
    }

    @Override
    public List<Duenio> listarEntidades() {
        return duenioRepository.findAll();
    }
}