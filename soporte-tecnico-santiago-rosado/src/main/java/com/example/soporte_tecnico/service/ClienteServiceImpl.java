package com.example.soporte_tecnico.service;

import com.example.soporte_tecnico.dto.ClienteDTO;
import com.example.soporte_tecnico.model.Cliente;
import com.example.soporte_tecnico.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación del servicio para la gestión de clientes
 * VERSIÓN DE EMERGENCIA - Sin inicialización automática en constructor
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private final AtomicLong counter = new AtomicLong(1);

    /**
     * Constructor vacío - SIN inicialización automática
     * Esto evita el error de NullPointerException
     */
    public ClienteServiceImpl() {
        // Constructor vacío - Spring se encargará de inyectar el repositorio
    }

    /**
     * Obtiene todos los clientes registrados en el sistema
     */
    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su ID único
     */
    @Override
    public Optional<Cliente> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return clienteRepository.findById(id);
    }

    /**
     * Guarda un nuevo cliente en el sistema a partir de un DTO
     */
    @Override
    public Cliente save(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            throw new IllegalArgumentException("El DTO del cliente no puede ser nulo");
        }

        // Validar campos obligatorios
        if (clienteDTO.getNombre() == null || clienteDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }

        if (clienteDTO.getEmail() == null || clienteDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email del cliente es obligatorio");
        }

        if (clienteDTO.getTelefono() == null || clienteDTO.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono del cliente es obligatorio");
        }

        // Crear nueva entidad Cliente a partir del DTO
        Cliente cliente = new Cliente();
        cliente.setId(counter.getAndIncrement()); // Generar ID automático
        cliente.setNombre(clienteDTO.getNombre().trim());
        cliente.setEmail(clienteDTO.getEmail().trim());
        cliente.setTelefono(clienteDTO.getTelefono().trim());

        // Guardar en el repositorio
        Cliente clienteGuardado = clienteRepository.save(cliente);

        System.out.println("Cliente creado exitosamente - ID: " + clienteGuardado.getId() +
                ", Nombre: " + clienteGuardado.getNombre());

        return clienteGuardado;
    }

    /**
     * Actualiza un cliente existente con la información proporcionada en el DTO
     */
    @Override
    public Cliente update(Long id, ClienteDTO clienteDTO) {
        // Validar parámetros
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        if (clienteDTO == null) {
            throw new IllegalArgumentException("El DTO del cliente no puede ser nulo");
        }

        // Buscar el cliente existente
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if (clienteExistente.isPresent()) {
            Cliente cliente = clienteExistente.get();

            // Actualizar solo los campos permitidos (no actualizamos el ID)
            if (clienteDTO.getNombre() != null && !clienteDTO.getNombre().trim().isEmpty()) {
                cliente.setNombre(clienteDTO.getNombre().trim());
            }

            if (clienteDTO.getEmail() != null && !clienteDTO.getEmail().trim().isEmpty()) {
                cliente.setEmail(clienteDTO.getEmail().trim());
            }

            if (clienteDTO.getTelefono() != null && !clienteDTO.getTelefono().trim().isEmpty()) {
                cliente.setTelefono(clienteDTO.getTelefono().trim());
            }

            // Guardar los cambios en el repositorio
            Cliente clienteActualizado = clienteRepository.update(cliente);

            System.out.println("Cliente actualizado exitosamente - ID: " + id +
                    ", Nuevo nombre: " + clienteActualizado.getNombre());

            return clienteActualizado;

        } else {
            // Lanzar excepción si el cliente no existe
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
    }

    /**
     * Elimina un cliente del sistema por su ID
     */
    @Override
    public void deleteById(Long id) {
        // Validar parámetro
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        // Verificar si el cliente existe antes de eliminar
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            System.out.println("Cliente con ID " + id + " eliminado correctamente");
        } else {
            System.out.println("No se pudo eliminar: Cliente con ID " + id + " no encontrado");
        }
    }

    /**
     * Busca clientes por su nombre (búsqueda parcial case-insensitive)
     */
    public List<Cliente> findByNombreContaining(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        return clienteRepository.findByNombreContaining(nombre);
    }

    /**
     * Busca un cliente por su dirección de email (búsqueda exacta)
     */
    public Optional<Cliente> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        return clienteRepository.findByEmail(email);
    }

    /**
     * Verifica si existe un cliente con el ID especificado
     */
    public boolean existsById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        return clienteRepository.existsById(id);
    }

    /**
     * Obtiene el número total de clientes registrados en el sistema
     */
    public long count() {
        return clienteRepository.count();
    }

    /**
     * Método de utilidad para inicializar datos de ejemplo manualmente
     * Se puede llamar desde el controlador si se necesitan datos de prueba
     */
    public void inicializarDatosEjemplo() {
        try {
            if (clienteRepository.count() == 0) {
                Cliente cliente1 = new Cliente();
                cliente1.setId(counter.getAndIncrement());
                cliente1.setNombre("Juan Pérez");
                cliente1.setEmail("juan@empresa.com");
                cliente1.setTelefono("123456789");

                Cliente cliente2 = new Cliente();
                cliente2.setId(counter.getAndIncrement());
                cliente2.setNombre("María García");
                cliente2.setEmail("maria@empresa.com");
                cliente2.setTelefono("987654321");

                clienteRepository.save(cliente1);
                clienteRepository.save(cliente2);

                System.out.println("Datos de ejemplo de clientes inicializados manualmente");
            }
        } catch (Exception e) {
            System.err.println("Error inicializando datos de ejemplo: " + e.getMessage());
        }
    }
}
