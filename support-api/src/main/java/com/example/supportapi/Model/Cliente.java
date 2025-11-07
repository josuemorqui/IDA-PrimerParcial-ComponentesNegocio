package com.example.supportapi.Model;

import jakarta.validation.constraints.NotBlank;

public class Cliente {
    private Long id;
    @NotBlank(message = "Nombre del cliente es obligatorio")
    private String nombre;
    private String telefono;
    private String email;

    // constructores, getters y setters
    public Cliente() {}
    public Cliente(Long id, String nombre, String telefono, String email) {
        this.id = id; this.nombre = nombre; this.telefono = telefono; this.email = email;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
