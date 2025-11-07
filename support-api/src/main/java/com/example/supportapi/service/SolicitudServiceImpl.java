package com.example.supportapi.service;

import com.example.supportapi.exception.ResourceNotFoundException;
import com.example.supportapi.Model.Solicitud;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private final Map<Long, Solicitud> storage = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1L);

    // ejemplo: crear algunos datos iniciales
    public SolicitudServiceImpl() {
        // datos de prueba opcionales
        // storage.put(...);
    }

    @Override
    public Solicitud create(Solicitud s) {
        long id = idGen.getAndIncrement();
        s.setId(id);
        if (s.getCreadoEn() == null) s.setCreadoEn(java.time.LocalDateTime.now());
        storage.put(id, s);
        return s;
    }

    @Override
    public List<Solicitud> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Solicitud> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Solicitud update(Long id, Solicitud s) {
        Solicitud existing = storage.get(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Solicitud no encontrada con id " + id);
        }
        // actualizar campos permitidos
        existing.setTitulo(s.getTitulo());
        existing.setDescripcion(s.getDescripcion());
        existing.setCliente(s.getCliente());
        existing.setEstado(s.getEstado() != null ? s.getEstado() : existing.getEstado());
        existing.setTecnico(s.getTecnico());
        storage.put(id, existing);
        return existing;
    }

    @Override
    public void delete(Long id) {
        if (storage.remove(id) == null) {
            throw new ResourceNotFoundException("Solicitud no encontrada con id " + id);
        }
    }
}
