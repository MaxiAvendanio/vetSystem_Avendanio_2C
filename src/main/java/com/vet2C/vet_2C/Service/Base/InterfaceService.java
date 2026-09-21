package com.vet2C.vet_2C.Service.Base;

import java.util.List;
import java.util.Optional;

public interface InterfaceService<T, R> {
    R registrarEntidad(T t);
    R buscarPorId(Long id);
    void eliminarEntidad(Long id);
    Optional<R> buscarEntidadPorString(String s);
    List<R> listarEntidades();
    R modificarEntidad(Long id, T t);
}
