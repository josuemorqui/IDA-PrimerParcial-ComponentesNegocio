package com.example.supportapi.controller;

import com.example.supportapi.dto.SolicitudRequest;
import com.example.supportapi.exception.ResourceNotFoundException;
import com.example.supportapi.Model.Solicitud;
import com.example.supportapi.service.SolicitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@Validated
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Solicitud> create(@Valid @RequestBody SolicitudRequest req) {
        Solicitud s = new Solicitud();
        s.setTitulo(req.getTitulo());
        s.setDescripcion(req.getDescripcion());
        s.setCliente(req.getCliente());
        s.setEstado("PENDIENTE");
        Solicitud created = service.create(s);
        return ResponseEntity.created(URI.create("/api/solicitudes/" + created.getId())).body(created);
    }

    // READ all
    @GetMapping
    public List<Solicitud> all() {
        return service.findAll();
    }

    // READ by id
    @GetMapping("/{id}")
    public Solicitud getById(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada con id " + id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Solicitud update(@PathVariable Long id, @Valid @RequestBody SolicitudRequest req) {
        Solicitud s = new Solicitud();
        s.setTitulo(req.getTitulo());
        s.setDescripcion(req.getDescripcion());
        s.setCliente(req.getCliente());
        // estado/técnico opcionales en cuerpo si lo deseas, aquí se mantiene simple
        return service.update(id, s);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

