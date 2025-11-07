package com.example.soporte_tecnico.controller;

import com.example.soporte_tecnico.dto.TecnicoDTO;
import com.example.soporte_tecnico.model.Tecnico;
import com.example.soporte_tecnico.service.TecnicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
@Tag(name = "Técnicos", description = "API para la gestión de técnicos de soporte")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @Operation(summary = "Obtener todos los técnicos",
            description = "Retorna una lista de todos los técnicos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de técnicos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Tecnico>> getAllTecnicos() {
        List<Tecnico> tecnicos = tecnicoService.findAll();
        return ResponseEntity.ok(tecnicos);
    }

    @Operation(summary = "Obtener técnico por ID",
            description = "Retorna un técnico específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Técnico encontrado",
                    content = @Content(schema = @Schema(implementation = Tecnico.class))),
            @ApiResponse(responseCode = "404", description = "Técnico no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> getTecnicoById(
            @Parameter(description = "ID del técnico a buscar", required = true)
            @PathVariable Long id) {
        return tecnicoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo técnico",
            description = "Registra un nuevo técnico en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Técnico creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Tecnico.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Tecnico> createTecnico(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del técnico a crear", required = true)
            @Valid @RequestBody TecnicoDTO tecnicoDTO) {
        Tecnico nuevoTecnico = tecnicoService.save(tecnicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTecnico);
    }

    @Operation(summary = "Actualizar técnico",
            description = "Actualiza los datos de un técnico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Técnico actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = Tecnico.class))),
            @ApiResponse(responseCode = "404", description = "Técnico no encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Tecnico> updateTecnico(
            @Parameter(description = "ID del técnico a actualizar", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TecnicoDTO tecnicoDTO) {
        try {
            Tecnico tecnicoActualizado = tecnicoService.update(id, tecnicoDTO);
            return ResponseEntity.ok(tecnicoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar técnico",
            description = "Elimina un técnico del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Técnico eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTecnico(
            @Parameter(description = "ID del técnico a eliminar", required = true)
            @PathVariable Long id) {
        tecnicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar técnicos por especialidad",
            description = "Retorna todos los técnicos que tienen una especialidad específica")
    @ApiResponse(responseCode = "200", description = "Lista de técnicos filtrada por especialidad")
    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<Tecnico>> getTecnicosByEspecialidad(
            @Parameter(description = "Especialidad del técnico (ej: Redes, Hardware, Software)", required = true)
            @PathVariable String especialidad) {
        List<Tecnico> tecnicos = tecnicoService.findByEspecialidad(especialidad);
        return ResponseEntity.ok(tecnicos);
    }

    @Operation(summary = "Health check",
            description = "Verifica que el controlador está funcionando correctamente")
    @ApiResponse(responseCode = "200", description = "Controlador funcionando correctamente")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("✅ Controlador de Técnicos funcionando correctamente");
    }
}