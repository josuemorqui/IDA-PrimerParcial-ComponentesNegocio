package com.example.soporte_tecnico.repository;

import com.example.soporte_tecnico.model.Tecnico;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del repositorio para la entidad Tecnico
 * Define las operaciones CRUD (Create, Read, Update, Delete) para técnicos
 *
 * Esta interfaz abstrae el acceso a datos de técnicos, permitiendo diferentes implementaciones
 * (memoria, base de datos, etc.) sin afectar el resto de la aplicación
 */
public interface TecnicoRepository {

    /**
     * Obtiene todos los técnicos registrados en el sistema
     * @return Lista de todos los técnicos (puede estar vacía si no hay técnicos)
     */
    List<Tecnico> findAll();

    /**
     * Busca un técnico por su ID único
     * @param id ID del técnico a buscar (no puede ser nulo)
     * @return Optional que contiene el técnico si existe, o vacío si no se encuentra
     * @throws IllegalArgumentException si el id es nulo
     */
    Optional<Tecnico> findById(Long id);

    /**
     * Busca técnicos por su nombre (búsqueda parcial case-insensitive)
     * @param nombre Nombre o parte del nombre a buscar (no puede ser nulo o vacío)
     * @return Lista de técnicos que coinciden con el nombre (puede estar vacía)
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    List<Tecnico> findByNombreContaining(String nombre);

    /**
     * Busca técnicos por su especialidad (búsqueda exacta case-insensitive)
     * @param especialidad Especialidad de los técnicos a buscar (no puede ser nulo o vacío)
     * @return Lista de técnicos que tienen la especialidad especificada (puede estar vacía)
     * @throws IllegalArgumentException si la especialidad es nulo o vacía
     */
    List<Tecnico> findByEspecialidad(String especialidad);

    /**
     * Guarda un nuevo técnico en el repositorio
     * Si el técnico no tiene ID, se le asignará uno automáticamente
     * @param tecnico Técnico a guardar (no puede ser nulo)
     * @return El técnico guardado con su ID asignado
     * @throws IllegalArgumentException si el técnico es nulo
     */
    Tecnico save(Tecnico tecnico);

    /**
     * Actualiza un técnico existente en el repositorio
     * El técnico debe existir previamente (tener un ID válido)
     * @param tecnico Técnico con los datos actualizados (no puede ser nulo)
     * @return El técnico actualizado
     * @throws RuntimeException si el técnico no existe en el repositorio
     */
    Tecnico update(Tecnico tecnico);

    /**
     * Elimina un técnico del repositorio por su ID
     * @param id ID del técnico a eliminar (no puede ser nulo)
     * @throws IllegalArgumentException si el id es nulo
     */
    void deleteById(Long id);

    /**
     * Verifica si existe un técnico con el ID especificado
     * @param id ID del técnico a verificar (no puede ser nulo)
     * @return true si existe un técnico con ese ID, false en caso contrario
     * @throws IllegalArgumentException si el id es nulo
     */
    boolean existsById(Long id);

    /**
     * Obtiene el número total de técnicos registrados en el sistema
     * @return Cantidad total de técnicos
     */
    long count();

    /**
     * Obtiene la lista de todas las especialidades únicas disponibles en el sistema
     * @return Lista de especialidades sin duplicados
     */
    List<String> findAllEspecialidades();
}
