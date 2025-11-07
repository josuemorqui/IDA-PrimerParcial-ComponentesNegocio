package com.example.soporte_tecnico.service;

import com.example.soporte_tecnico.model.Solicitud;
import com.example.soporte_tecnico.model.Cliente;
import com.example.soporte_tecnico.model.Tecnico;
import org.springframework.stereotype.Service;
import com.example.soporte_tecnico.exception.SolicitudNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación del servicio de solicitudes usando almacenamiento en memoria
 */
@Service
public class SolicitudServiceImpl implements SolicitudService {

    // Simulamos una base de datos en memoria
    private final List<Solicitud> solicitudes = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1); // Generador de IDs

    public SolicitudServiceImpl() {
        // Datos de ejemplo para pruebas
        inicializarDatosEjemplo();
    }

    private void inicializarDatosEjemplo() {
        // Crear clientes de ejemplo
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNombre("Juan Pérez");
        cliente1.setEmail("juan@empresa.com");
        cliente1.setTelefono("123456789");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNombre("María García");
        cliente2.setEmail("maria@empresa.com");
        cliente2.setTelefono("987654321");

        // Crear técnicos de ejemplo
        Tecnico tecnico1 = new Tecnico();
        tecnico1.setId(1L);
        tecnico1.setNombre("Carlos López");
        tecnico1.setEspecialidad("Redes");

        Tecnico tecnico2 = new Tecnico();
        tecnico2.setId(2L);
        tecnico2.setNombre("Ana Martínez");
        tecnico2.setEspecialidad("Software");

        // Crear solicitudes de ejemplo
        Solicitud solicitud1 = new Solicitud();
        solicitud1.setId(counter.getAndIncrement());
        solicitud1.setDescripcion("No puedo conectarme a la red WiFi");
        solicitud1.setFechaCreacion(LocalDateTime.now().minusDays(2));
        solicitud1.setEstado("EN_PROCESO");
        solicitud1.setCliente(cliente1);
        solicitud1.setTecnico(tecnico1);

        Solicitud solicitud2 = new Solicitud();
        solicitud2.setId(counter.getAndIncrement());
        solicitud2.setDescripcion("Error al iniciar el sistema");
        solicitud2.setFechaCreacion(LocalDateTime.now().minusDays(1));
        solicitud2.setEstado("PENDIENTE");
        solicitud2.setCliente(cliente2);
        solicitud2.setTecnico(tecnico2);

        solicitudes.add(solicitud1);
        solicitudes.add(solicitud2);
    }

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
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        if (solicitud.getId() == null) {
            solicitud.setId(counter.getAndIncrement());
        }

        if (solicitud.getFechaCreacion() == null) {
            solicitud.setFechaCreacion(LocalDateTime.now());
        }

        if (solicitud.getEstado() == null || solicitud.getEstado().trim().isEmpty()) {
            solicitud.setEstado("PENDIENTE");
        }

        solicitudes.add(solicitud);
        return solicitud;
    }

    @Override
    public Solicitud update(Long id, Solicitud solicitud) {
        Optional<Solicitud> existingSolicitud = findById(id);

        if (existingSolicitud.isPresent()) {
            Solicitud toUpdate = existingSolicitud.get();

            toUpdate.setDescripcion(solicitud.getDescripcion());
            toUpdate.setEstado(solicitud.getEstado());
            toUpdate.setCliente(solicitud.getCliente());
            toUpdate.setTecnico(solicitud.getTecnico());

            return toUpdate;
        } else {
            throw new SolicitudNotFoundException("Solicitud no encontrada con ID: " + id);
        }
    }

    @Override
    public void deleteById(Long id) {
        solicitudes.removeIf(s -> s.getId().equals(id));
    }
}