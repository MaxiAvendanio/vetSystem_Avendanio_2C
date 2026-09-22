package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.DuenioRequestDTO;
import com.vet2C.vet_2C.DTO.DuenioResponseDTO;
import com.vet2C.vet_2C.Exception.ErrorResponse;
import com.vet2C.vet_2C.Service.DuenioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Duenios", description = "CRUD - clinica veterinaria-Duenio")
@RestController
@RequestMapping("/api/duenio")
@RequiredArgsConstructor
public class DuenioController {

    private final DuenioService duenioService;

    @Operation(
            summary = "Registro de nuevos duenios",
            description = "Crea un nuevo duenio. Todos los valores deben estar completos ya que: la cedula debe estar comprendida entre 7 y 8 caracteres"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Duenio creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Los datos son invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "cedula ya registrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping
    public ResponseEntity<DuenioResponseDTO> registrarDuenio(@Valid @RequestBody DuenioRequestDTO duenioRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(duenioService.registrarEntidad(duenioRequestDto));
    }

    @Operation(
            summary = "Busca duenio por ID",
            description = "Devuelve los datos del duenio con el ID indicado; sino existe devuelve 404"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Duenio encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe duenio con ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DuenioResponseDTO> buscarPorId(@Parameter(description = "ID del duenio a buscar", example = "1") @PathVariable("id") Long id) {
        return ResponseEntity.ok(duenioService.buscarPorId(id));
    }

    @Operation(
            summary = "Lista todos los duenios existentes en la clinica",
            description = "Devuelve la lista completa de todos los duenios registrados, si no hay duenios devuelve una lista vacia con 200"
    )
    @GetMapping
    public List<DuenioResponseDTO> listarTodos() {
        return duenioService.listarEntidades();
    }

    @Operation(
            summary = "Modifica un duenio existente",
            description = "Actualiza los datos del duenio con el ID indicado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Duenio modificado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Los datos son invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe duenio con ese ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<DuenioResponseDTO> modificarDuenio(@Parameter(description = "ID del duenio a modificar", example = "1") @PathVariable Long id,
                                                             @Valid @RequestBody DuenioRequestDTO duenio) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(duenioService.modificarEntidad(id, duenio));
    }

    @Operation(
            summary = "Elimina un duenio",
            description = "Borra el duenio con el ID indicado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Duenio eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe duenio con ese ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDuenio(@Parameter(description = "ID del duenio a eliminar", example = "1") @PathVariable Long id) {
        duenioService.eliminarEntidad(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Busca duenio por nombre",
            description = "Devuelve el duenio que coincida con el nombre indicado, sin distinguir mayúsculas/minúsculas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Duenio encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe duenio con ese nombre")
    })
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<Optional<DuenioResponseDTO>> buscarPorNombre(@Parameter(description = "Nombre del duenio", example = "Maxi") @PathVariable String nombre) {
        Optional<DuenioResponseDTO> duenioBuscado = duenioService.buscarEntidadPorString(nombre);
        return duenioBuscado.isPresent() ? ResponseEntity.ok(duenioBuscado) : ResponseEntity.notFound().build();
    }
}