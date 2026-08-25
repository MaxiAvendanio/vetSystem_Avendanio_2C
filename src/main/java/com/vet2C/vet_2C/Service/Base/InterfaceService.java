package com.vet2C.vet_2C.Service.Base;

import java.util.List;
import java.util.Optional;

public interface InterfaceService<T> {
    T registrarEntidad(T t);
    Optional<T> buscarPorId(Long id);
    void eliminarEntidad(Long id);
    Optional<T> buscarEntidadPorString(String s);
    List<T> listarEntidades();
    T modificarEntidad(T t);
}
