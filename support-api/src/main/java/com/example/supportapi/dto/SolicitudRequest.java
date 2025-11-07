package com.example.supportapi.dto;

import com.example.supportapi.Model.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SolicitudRequest{
    @NotBlank(message = "Título es obligatorio")
    private String titulo;

    @NotBlank(message = "Descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "Información del cliente es requerida")
    private Cliente cliente;

    // getters / setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
