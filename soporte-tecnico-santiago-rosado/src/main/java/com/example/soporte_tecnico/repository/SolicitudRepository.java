package com.example.soporte_tecnico.repository;

import com.example.soporte_tecnico.model.Solicitud;
import java.util.List;
import java.util.Optional;

public interface SolicitudRepository {
    List<Solicitud> findAll();
    Optional<Solicitud> findById(Long id);
    Solicitud save(Solicitud solicitud);
    Solicitud update(Solicitud solicitud);
    void deleteById(Long id);
    boolean existsById(Long id);
}
