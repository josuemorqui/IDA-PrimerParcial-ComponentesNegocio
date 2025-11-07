package com.example.soporte_tecnico.controller;

import com.example.soporte_tecnico.model.Solicitud;
import com.example.soporte_tecnico.service.SolicitudService;
import com.example.soporte_tecnico.exception.SolicitudNotFoundException;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/solicitudes")
@Tag(name = "Solicitudes", description = "API para la gestión de solicitudes de soporte técnico")
public class SolicitudController {

    private final SolicitudService solicitudService;

    @Autowired
    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @Operation(summary = "Obtener todas las solicitudes",
            description = "Retorna una lista de todas las solicitudes de soporte técnico")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Solicitud>> getAllSolicitudes() {
        List<Solicitud> solicitudes = solicitudService.findAll();
        return ResponseEntity.ok(solicitudes);
    }

    @Operation(summary = "Obtener solicitud por ID",
            description = "Retorna una solicitud específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada",
                    content = @Content(schema = @Schema(implementation = Solicitud.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> getSolicitudById(
            @Parameter(description = "ID de la solicitud a buscar", required = true)
            @PathVariable Long id) {
        Optional<Solicitud> solicitud = solicitudService.findById(id);
        return solicitud.map(ResponseEntity::ok)
                .orElseThrow(() -> new SolicitudNotFoundException("Solicitud no encontrada con ID: " + id));
    }

    @Operation(summary = "Crear nueva solicitud",
            description = "Crea una nueva solicitud de soporte técnico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Solicitud.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Solicitud> createSolicitud(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la solicitud a crear", required = true)
            @Valid @RequestBody Solicitud solicitud) {
        Solicitud nuevaSolicitud = solicitudService.save(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSolicitud);
    }

    @Operation(summary = "Actualizar solicitud",
            description = "Actualiza los datos de una solicitud existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = Solicitud.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> updateSolicitud(
            @Parameter(description = "ID de la solicitud a actualizar", required = true)
            @PathVariable Long id,
            @Valid @RequestBody Solicitud solicitud) {
        try {
            Solicitud solicitudActualizada = solicitudService.update(id, solicitud);
            return ResponseEntity.ok(solicitudActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar solicitud",
            description = "Elimina una solicitud del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Solicitud eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolicitud(
            @Parameter(description = "ID de la solicitud a eliminar", required = true)
            @PathVariable Long id) {
        solicitudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}