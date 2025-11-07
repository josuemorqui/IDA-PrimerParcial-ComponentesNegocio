package com.example.soporte_tecnico.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Representa una solicitud de soporte técnico con toda su información
 */
public class Solicitud {
    private Long id;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    private LocalDateTime fechaCreacion;

    @NotBlank(message = "El estado es obligatorio")
    private String estado; // Ej: "PENDIENTE", "EN_PROCESO", "RESUELTO"

    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;

    @NotNull(message = "El técnico asignado es obligatorio")
    private Tecnico tecnico;

    // Constructor vacío
    public Solicitud() {}

    // Constructor con parámetros
    public Solicitud(Long id, String descripcion, LocalDateTime fechaCreacion,
                     String estado, Cliente cliente, Tecnico tecnico) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.cliente = cliente;
        this.tecnico = tecnico;
    }

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }
}

