package com.example.soporte_tecnico.repository;

import com.example.soporte_tecnico.model.Cliente;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del repositorio para la entidad Cliente
 * Define las operaciones CRUD (Create, Read, Update, Delete) para clientes
 *
 * Esta interfaz sigue el patrón Repository que abstrae el acceso a datos
 * Permite cambiar la implementación (memoria, base de datos, etc.) sin afectar el servicio
 */
public interface ClienteRepository {

    /**
     * Obtiene todos los clientes registrados en el sistema
     * @return Lista de todos los clientes (puede estar vacía si no hay clientes)
     */
    List<Cliente> findAll();

    /**
     * Busca un cliente por su ID único
     * @param id ID del cliente a buscar (no puede ser nulo)
     * @return Optional que contiene el cliente si existe, o vacío si no se encuentra
     * @throws IllegalArgumentException si el id es nulo
     */
    Optional<Cliente> findById(Long id);

    /**
     * Busca clientes por su nombre (búsqueda parcial case-insensitive)
     * @param nombre Nombre o parte del nombre a buscar (no puede ser nulo o vacío)
     * @return Lista de clientes que coinciden con el nombre (puede estar vacía)
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    List<Cliente> findByNombreContaining(String nombre);

    /**
     * Busca un cliente por su dirección de email (búsqueda exacta)
     * @param email Email del cliente a buscar (no puede ser nulo o vacío)
     * @return Optional que contiene el cliente si existe, o vacío si no se encuentra
     * @throws IllegalArgumentException si el email es nulo o vacío
     */
    Optional<Cliente> findByEmail(String email);

    /**
     * Guarda un nuevo cliente en el repositorio
     * Si el cliente no tiene ID, se le asignará uno automáticamente
     * @param cliente Cliente a guardar (no puede ser nulo)
     * @return El cliente guardado con su ID asignado
     * @throws IllegalArgumentException si el cliente es nulo
     */
    Cliente save(Cliente cliente);

    /**
     * Actualiza un cliente existente en el repositorio
     * El cliente debe existir previamente (tener un ID válido)
     * @param cliente Cliente con los datos actualizados (no puede ser nulo)
     * @return El cliente actualizado
     * @throws RuntimeException si el cliente no existe en el repositorio
     */
    Cliente update(Cliente cliente);

    /**
     * Elimina un cliente del repositorio por su ID
     * @param id ID del cliente a eliminar (no puede ser nulo)
     * @throws IllegalArgumentException si el id es nulo
     */
    void deleteById(Long id);

    /**
     * Verifica si existe un cliente con el ID especificado
     * @param id ID del cliente a verificar (no puede ser nulo)
     * @return true si existe un cliente con ese ID, false en caso contrario
     * @throws IllegalArgumentException si el id es nulo
     */
    boolean existsById(Long id);

    /**
     * Obtiene el número total de clientes registrados en el sistema
     * @return Cantidad total de clientes
     */
    long count();
}
