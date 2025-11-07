package com.example.soporte_tecnico.repository;

import com.example.soporte_tecnico.model.Cliente;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación concreta del repositorio de clientes usando almacenamiento en memoria
 *
 * @Repository Indica que esta clase es un componente de repositorio de Spring
 *             (permite la inyección de dependencias y el manejo de excepciones)
 *
 * Esta implementación usa una ArrayList en memoria para simular una base de datos
 * Es ideal para desarrollo y pruebas, pero en producción se reemplazaría por una BD real
 */
@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    // Simulación de tabla de clientes en memoria
    private final List<Cliente> clientes = new ArrayList<>();

    // Generador de IDs automáticos (simula AUTO_INCREMENT de base de datos)
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Obtiene todos los clientes registrados en el sistema
     * @return Lista inmutable de todos los clientes
     */
    @Override
    public List<Cliente> findAll() {
        // Retornamos una copia para evitar modificaciones externas a la lista interna
        return new ArrayList<>(clientes);
    }

    /**
     * Busca un cliente por su ID único
     * @param id ID del cliente a buscar
     * @return Optional con el cliente si existe, o vacío si no se encuentra
     * @throws IllegalArgumentException si el id es nulo
     */
    @Override
    public Optional<Cliente> findById(Long id) {
        // Validar parámetro de entrada
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        // Buscar cliente por ID usando Stream API
        return clientes.stream()
                .filter(cliente -> id.equals(cliente.getId()))
                .findFirst();
    }

    /**
     * Busca clientes por su nombre (búsqueda parcial case-insensitive)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de clientes que coinciden con el nombre
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    @Override
    public List<Cliente> findByNombreContaining(String nombre) {
        // Validar parámetro de entrada
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        String nombreLower = nombre.toLowerCase();

        // Filtrar clientes cuyo nombre contenga el texto buscado (case-insensitive)
        return clientes.stream()
                .filter(cliente -> cliente.getNombre().toLowerCase().contains(nombreLower))
                .toList(); // Java 16+ - equivalente a .collect(Collectors.toList())
    }

    /**
     * Busca un cliente por su dirección de email (búsqueda exacta case-insensitive)
     * @param email Email del cliente a buscar
     * @return Optional con el cliente si existe, o vacío si no se encuentra
     * @throws IllegalArgumentException si el email es nulo o vacío
     */
    @Override
    public Optional<Cliente> findByEmail(String email) {
        // Validar parámetro de entrada
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        String emailLower = email.toLowerCase();

        // Buscar cliente por email exacto (case-insensitive)
        return clientes.stream()
                .filter(cliente -> emailLower.equals(cliente.getEmail().toLowerCase()))
                .findFirst();
    }

    /**
     * Guarda un nuevo cliente en el repositorio
     * Si el cliente no tiene ID, se le asignará uno automáticamente
     * @param cliente Cliente a guardar
     * @return El cliente guardado con su ID asignado
     * @throws IllegalArgumentException si el cliente es nulo
     */
    @Override
    public Cliente save(Cliente cliente) {
        // Validar parámetro de entrada
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        // Asignar ID automático si no tiene
        if (cliente.getId() == null) {
            cliente.setId(idGenerator.getAndIncrement());
        } else {
            // Verificar que el ID no esté duplicado
            if (existsById(cliente.getId())) {
                throw new IllegalArgumentException("Ya existe un cliente con ID: " + cliente.getId());
            }
        }

        // Agregar cliente a la lista
        clientes.add(cliente);

        System.out.println("Cliente guardado - ID: " + cliente.getId() + ", Nombre: " + cliente.getNombre());

        return cliente;
    }

    /**
     * Actualiza un cliente existente en el repositorio
     * @param cliente Cliente con los datos actualizados
     * @return El cliente actualizado
     * @throws RuntimeException si el cliente no existe en el repositorio
     */
    @Override
    public Cliente update(Cliente cliente) {
        // Validar parámetro de entrada
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        // Buscar el cliente existente
        Optional<Cliente> clienteExistente = findById(cliente.getId());

        if (clienteExistente.isPresent()) {
            // Eliminar el cliente antiguo
            deleteById(cliente.getId());

            // Agregar el cliente actualizado
            clientes.add(cliente);

            System.out.println("Cliente actualizado - ID: " + cliente.getId() + ", Nombre: " + cliente.getNombre());

            return cliente;
        } else {
            throw new RuntimeException("No se puede actualizar: Cliente no encontrado con ID: " + cliente.getId());
        }
    }

    /**
     * Elimina un cliente del repositorio por su ID
     * @param id ID del cliente a eliminar
     * @throws IllegalArgumentException si el id es nulo
     */
    @Override
    public void deleteById(Long id) {
        // Validar parámetro de entrada
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        // Eliminar cliente si existe
        boolean removed = clientes.removeIf(cliente -> id.equals(cliente.getId()));

        if (removed) {
            System.out.println("Cliente eliminado - ID: " + id);
        } else {
            System.out.println("No se encontró cliente con ID: " + id + " para eliminar");
        }
    }

    /**
     * Verifica si existe un cliente con el ID especificado
     * @param id ID del cliente a verificar
     * @return true si existe un cliente con ese ID, false en caso contrario
     * @throws IllegalArgumentException si el id es nulo
     */
    @Override
    public boolean existsById(Long id) {
        // Validar parámetro de entrada
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        return clientes.stream().anyMatch(cliente -> id.equals(cliente.getId()));
    }

    /**
     * Obtiene el número total de clientes registrados en el sistema
     * @return Cantidad total de clientes
     */
    @Override
    public long count() {
        return clientes.size();
    }

    /**
     * Método de utilidad para limpiar todos los clientes (útil para testing)
     * ATENCIÓN: Este método elimina todos los datos, usar con cuidado
     */
    public void deleteAll() {
        clientes.clear();
        idGenerator.set(1); // Reiniciar el generador de IDs
        System.out.println("Todos los clientes han sido eliminados");
    }

    /**
     * Método de utilidad para inicializar datos de ejemplo
     * Se puede llamar desde el servicio o desde tests
     */
    public void initializeSampleData() {
        if (clientes.isEmpty()) {
            save(new Cliente(null, "Juan Pérez", "juan@empresa.com", "123456789"));
            save(new Cliente(null, "María García", "maria@empresa.com", "987654321"));
            save(new Cliente(null, "Carlos López", "carlos@empresa.com", "555123456"));
            System.out.println("Datos de ejemplo de clientes inicializados");
        }
    }
}
