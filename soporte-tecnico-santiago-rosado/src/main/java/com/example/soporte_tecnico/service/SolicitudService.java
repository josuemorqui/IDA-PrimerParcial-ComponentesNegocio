package com.example.soporte_tecnico.service;

import com.example.soporte_tecnico.model.Solicitud;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los servicios disponibles para gestionar solicitudes
 */
public interface SolicitudService {

    /**
     * Obtiene todas las solicitudes de soporte técnico
     */
    List<Solicitud> findAll();

    /**
     * Busca una solicitud por su ID
     */
    Optional<Solicitud> findById(Long id);

    /**
     * Guarda una nueva solicitud de soporte técnico
     */
    Solicitud save(Solicitud solicitud);

    /**
     * Actualiza una solicitud existente
     */
    Solicitud update(Long id, Solicitud solicitud);

    /**
     * Elimina una solicitud por su ID
     */
    void deleteById(Long id);
}