package com.example.soporte_tecnico.service;

import com.example.soporte_tecnico.dto.TecnicoDTO;
import com.example.soporte_tecnico.model.Tecnico;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio para la gestión de técnicos en el sistema
 * Define las operaciones de negocio relacionadas con la entidad Tecnico
 *
 * Esta interfaz sigue el principio de inversión de dependencias (SOLID)
 * Permite diferentes implementaciones sin afectar a los controladores
 */
public interface TecnicoService {

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
     * Guarda un nuevo técnico en el sistema a partir de un DTO
     * Convierte el DTO en entidad, aplica reglas de negocio y persiste el técnico
     *
     * @param tecnicoDTO Objeto DTO con los datos del nuevo técnico (no puede ser nulo)
     * @return El técnico guardado con su ID generado automáticamente
     * @throws IllegalArgumentException si el tecnicoDTO es nulo
     */
    Tecnico save(TecnicoDTO tecnicoDTO);

    /**
     * Actualiza un técnico existente con la información proporcionada en el DTO
     * Busca el técnico por ID, actualiza sus campos y aplica validaciones de negocio
     *
     * @param id ID del técnico a actualizar (no puede ser nulo)
     * @param tecnicoDTO DTO con los nuevos datos del técnico (no puede ser nulo)
     * @return El técnico actualizado
     * @throws RuntimeException si no se encuentra el técnico con el ID especificado
     * @throws IllegalArgumentException si alguno de los parámetros es nulo
     */
    Tecnico update(Long id, TecnicoDTO tecnicoDTO);

    /**
     * Elimina un técnico del sistema por su ID
     * Realiza validaciones antes de proceder con la eliminación
     *
     * @param id ID del técnico a eliminar (no puede ser nulo)
     * @throws IllegalArgumentException si el id es nulo
     */
    void deleteById(Long id);

    /**
     * Busca técnicos por su especialidad (búsqueda exacta case-insensitive)
     * @param especialidad Especialidad de los técnicos a buscar (no puede ser nula o vacía)
     * @return Lista de técnicos que tienen la especialidad especificada (puede estar vacía)
     * @throws IllegalArgumentException si la especialidad es nula o vacía
     */
    List<Tecnico> findByEspecialidad(String especialidad);

    /**
     * Busca técnicos por su nombre (búsqueda parcial case-insensitive)
     * @param nombre Nombre o parte del nombre a buscar (no puede ser nulo o vacío)
     * @return Lista de técnicos que coinciden con el nombre (puede estar vacía)
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    List<Tecnico> findByNombreContaining(String nombre);

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
