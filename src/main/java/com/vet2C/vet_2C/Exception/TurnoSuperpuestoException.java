package com.vet2C.vet_2C.Exception;

import com.vet2C.vet_2C.DTO.TurnoRequestDTO;

public class TurnoSuperpuestoException extends RuntimeException{
    public TurnoSuperpuestoException(String message){
        super(message);
    }
}
