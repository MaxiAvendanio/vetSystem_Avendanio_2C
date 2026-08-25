package com.vet2C.vet_2C.Service;


import com.vet2C.vet_2C.Entity.Mascota;
import com.vet2C.vet_2C.Repository.MascotaRepository;
import com.vet2C.vet_2C.Service.Base.InterfaceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class MascotaService implements InterfaceService<Mascota> {
    private final MascotaRepository mascotaRepository;

    @Override
    public Mascota registrarEntidad(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    @Override
    public Optional<Mascota> buscarPorId(Long id) {
        return mascotaRepository.findById(id);
    }

    @Override
    public Mascota modificarEntidad(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    @Override
    public void eliminarEntidad(Long id) {
        Optional<Mascota> mascota = mascotaRepository.findById(id);
        if(mascota.isPresent())
        {
            mascotaRepository.deleteById(id);
        }
    }

    @Override
    public Optional <Mascota> buscarEntidadPorString(String s) {
        return mascotaRepository.findByNombreIgnoreCase(s);
    }

    @Override
    public List<Mascota> listarEntidades() {
        return mascotaRepository.findAll();
    }

    public boolean existeMascotaPorNombreYDuenio(String nombre, Long duenioId) {
        return mascotaRepository.existsByNombreAndDuenioId(nombre, duenioId);
    }

    public long contarPorEspecie(String especie) {
        return mascotaRepository.countByEspecieIgnoreCase(especie);
    }

    public Optional<Mascota> buscarPorRaza(String raza) {
        return mascotaRepository.findByRazaIgnoreCase(raza);
    }

    public List<Mascota> listarPorDuenio(Long duenioId) {
        return mascotaRepository.findByDuenioId(duenioId);
    }
}