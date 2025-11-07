package com.example.soporte_tecnico.repository;

import com.example.soporte_tecnico.model.Solicitud;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SolicitudRepositoryImpl implements SolicitudRepository {

    private final List<Solicitud> solicitudes = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public List<Solicitud> findAll() {
        return new ArrayList<>(solicitudes);
    }

    @Override
    public Optional<Solicitud> findById(Long id) {
        return solicitudes.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        if (solicitud.getId() == null) {
            solicitud.setId(counter.getAndIncrement());
        }
        solicitudes.add(solicitud);
        return solicitud;
    }

    @Override
    public Solicitud update(Solicitud solicitud) {
        deleteById(solicitud.getId());
        solicitudes.add(solicitud);
        return solicitud;
    }

    @Override
    public void deleteById(Long id) {
        solicitudes.removeIf(s -> s.getId().equals(id));
    }

    @Override
    public boolean existsById(Long id) {
        return solicitudes.stream().anyMatch(s -> s.getId().equals(id));
    }
}
