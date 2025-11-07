// Solicitud.java
package com.example.supportapi.Model;

import java.time.LocalDateTime;

public class Solicitud {
    private Long id;
    private String titulo;
    private String descripcion;
    private Cliente cliente;   // puede ser sólo info básica
    private Tecnico tecnico;   // técnico asignado opcional
    private String estado;     // e.g., "PENDIENTE", "EN_PROGRESO", "CERRADO"
    private LocalDateTime creadoEn;

    public Solicitud() {}
    public Solicitud(Long id, String titulo, String descripcion, Cliente cliente, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cliente = cliente;
        this.estado = estado;
        this.creadoEn = LocalDateTime.now();
    }
    // getters y setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}
