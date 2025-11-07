package com.example.soporte_tecnico.exception;

/**
 * Excepción personalizada para cuando no se encuentra una solicitud
 */
public class SolicitudNotFoundException extends RuntimeException {
    public SolicitudNotFoundException(String message) {
        super(message);
    }
}
