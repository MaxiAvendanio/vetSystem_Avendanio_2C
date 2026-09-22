package com.vet2C.vet_2C.Controller;

import com.vet2C.vet_2C.DTO.MascotaRequestDTO;
import com.vet2C.vet_2C.DTO.MascotaResponseDTO;
import com.vet2C.vet_2C.Exception.ErrorResponse;
import com.vet2C.vet_2C.Service.MascotaService;
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

@Tag(name = "Mascotas", description = "CRUD - clinica veterinaria-Mascota")
@RestController
@RequestMapping("/api/mascota")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @Operation(
            summary = "Registro de nuevas mascotas",
            description = "Crea una nueva mascota asociada a un duenio existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mascota creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Los datos son invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe el duenio indicado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe una mascota con ese nombre para el duenio",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping
    public ResponseEntity<MascotaResponseDTO> registrarMascota(@Valid @RequestBody MascotaRequestDTO mascotaRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mascotaService.registrarEntidad(mascotaRequestDto));
    }

    @Operation(summary = "Modifica una mascota existente", description = "Actualiza los datos de la mascota con el ID indicado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota modificada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Los datos son invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe mascota con ese ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> modificarMascota(@Parameter(description = "ID de la mascota", example = "1") @PathVariable Long id,
                                                               @Valid @RequestBody MascotaRequestDTO mascotaRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mascotaService.modificarEntidad(id, mascotaRequestDto));
    }

    @Operation(summary = "Elimina una mascota", description = "Borra la mascota con el ID indicado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mascota eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe mascota con ese ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@Parameter(description = "ID de la mascota", example = "1") @PathVariable Long id) {
        mascotaService.eliminarEntidad(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca mascota por ID", description = "Devuelve los datos de la mascota con el ID indicado; sino existe devuelve 404")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe mascota con ese ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> buscarPorId(@Parameter(description = "ID de la mascota", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.buscarPorId(id));
    }

    @Operation(summary = "Lista todas las mascotas", description = "Devuelve la lista completa de mascotas registradas")
    @GetMapping
    public List<MascotaResponseDTO> listarTodos() {
        return mascotaService.listarEntidades();
    }

    @Operation(summary = "Verifica existencia de mascota", description = "Indica si ya existe una mascota con ese nombre para el duenio indicado")
    @GetMapping("/existe")
    public boolean existeMascota(@Parameter(example = "Negro") @RequestParam String nombre,
                                 @Parameter(example = "2") @RequestParam Long duenioId) {
        return mascotaService.existeMascotaPorNombreYDuenio(nombre, duenioId);
    }

    @Operation(summary = "Cuenta mascotas por especie", description = "Devuelve la cantidad de mascotas registradas de la especie indicada")
    @GetMapping("/contar")
    public long contarPorEspecie(@Parameter(example = "Perro") @RequestParam String especie) {
        return mascotaService.contarPorEspecie(especie);
    }

    @Operation(summary = "Busca mascota por raza", description = "Devuelve una mascota que coincida con la raza indicada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe mascota con esa raza",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/raza/{raza}")
    public MascotaResponseDTO buscarPorRaza(@Parameter(example = "Labrador") @PathVariable String raza) {
        return mascotaService.buscarPorRaza(raza);
    }

    @Operation(summary = "Lista mascotas de un duenio", description = "Devuelve todas las mascotas asociadas al duenio indicado")
    @GetMapping("/duenio/{duenioId}")
    public List<MascotaResponseDTO> listarPorDuenio(@Parameter(example = "2") @PathVariable Long duenioId) {
        return mascotaService.listarPorDuenio(duenioId);
    }
}