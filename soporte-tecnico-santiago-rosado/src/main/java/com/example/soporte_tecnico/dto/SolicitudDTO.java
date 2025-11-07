package com.example.soporte_tecnico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SolicitudDTO {
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID del técnico es obligatorio")
    private Long tecnicoId;

    // Constructores, getters y setters
    public SolicitudDTO() {}

    public SolicitudDTO(String descripcion, String estado, Long clienteId, Long tecnicoId) {
        this.descripcion = descripcion;
        this.estado = estado;
        this.clienteId = clienteId;
        this.tecnicoId = tecnicoId;
    }

    // Getters y Setters...
}
