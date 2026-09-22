package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.VeterinarioRequestDTO;
import com.vet2C.vet_2C.DTO.VeterinarioResponseDTO;
import com.vet2C.vet_2C.Exception.ErrorResponse;
import com.vet2C.vet_2C.Service.VeterinarioService;
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

@Tag(name = "Veterinarios", description = "CRUD - clinica veterinaria-Veterinario")
@RestController
@RequestMapping("/api/veterinario")
@RequiredArgsConstructor
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    @Operation(summary = "Registro de nuevos veterinarios", description = "Crea un nuevo veterinario. La matricula debe tener el formato MP-XXXX")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Veterinario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Los datos son invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Matricula ya registrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping
    public ResponseEntity<VeterinarioResponseDTO> registrarVeterinario(@Valid @RequestBody VeterinarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.registrarEntidad(dto));
    }

    @Operation(summary = "Busca veterinario por ID", description = "Devuelve los datos del veterinario con el ID indicado; sino existe devuelve 404")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veterinario encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe veterinario con ese ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioResponseDTO> buscarPorId(@Parameter(example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.buscarPorId(id));
    }

    @Operation(summary = "Lista todos los veterinarios", description = "Devuelve la lista completa de veterinarios registrados")
    @GetMapping
    public List<VeterinarioResponseDTO> listarTodos() {
        return veterinarioService.listarEntidades();
    }

    @Operation(summary = "Busca veterinario por nombre", description = "Devuelve el veterinario que coincida con el nombre indicado")
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<Optional<VeterinarioResponseDTO>> buscarPorNombre(@Parameter(example = "Ana") @PathVariable String nombre) {
        Optional<VeterinarioResponseDTO> encontrado = veterinarioService.buscarEntidadPorString(nombre);
        return encontrado.isPresent() ? ResponseEntity.ok(encontrado) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Modifica un veterinario existente", description = "Actualiza los datos del veterinario con el ID indicado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veterinario modificado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Los datos son invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe veterinario con ese ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioResponseDTO> modificarVeterinario(@Parameter(example = "1") @PathVariable Long id,
                                                                       @Valid @RequestBody VeterinarioRequestDTO dto) {
        return ResponseEntity.ok(veterinarioService.modificarEntidad(id, dto));
    }

    @Operation(summary = "Elimina un veterinario", description = "Borra el veterinario con el ID indicado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Veterinario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe veterinario con ese ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVeterinario(@Parameter(example = "1") @PathVariable Long id) {
        veterinarioService.eliminarEntidad(id);
        return ResponseEntity.noContent().build();
    }
}