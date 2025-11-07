package com.example.supportapi.service;

import com.example.supportapi.Model.Solicitud;

import java.util.List;
import java.util.Optional;

public interface SolicitudService {
    Solicitud create(Solicitud s);
    List<Solicitud> findAll();
    Optional<Solicitud> findById(Long id);
    Solicitud update(Long id, Solicitud s);
    void delete(Long id);
}
