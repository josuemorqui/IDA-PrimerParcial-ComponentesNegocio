package com.example.soporte_tecnico.service;

import com.example.soporte_tecnico.dto.TecnicoDTO;
import com.example.soporte_tecnico.model.Tecnico;
import com.example.soporte_tecnico.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación del servicio para la gestión de técnicos
 * Contiene la lógica de negocio para las operaciones CRUD de técnicos
 *
 * @Service Indica que esta clase es un componente de servicio de Spring
 *          (permite la inyección de dependencias y el manejo de transacciones)
 *
 * Esta clase actúa como intermediario entre el controlador y el repositorio,
 * aplicando reglas de negocio y validaciones adicionales
 */
@Service
public class TecnicoServiceImpl implements TecnicoService {

    // Inyección del repositorio para acceder a los datos de técnicos
    @Autowired
    private TecnicoRepository tecnicoRepository;

    // Contador para generar IDs automáticos (comienza en 7 porque ya tenemos 6 técnicos de ejemplo)
    private final AtomicLong idCounter = new AtomicLong(7);

    /**
     * Obtiene todos los técnicos registrados en el sistema
     * @return Lista de todos los técnicos
     */
    @Override
    public List<Tecnico> findAll() {
        // Delegar la operación al repositorio
        List<Tecnico> tecnicos = tecnicoRepository.findAll();

        // Podríamos aplicar lógica adicional aquí (filtros, transformaciones, etc.)
        System.out.println("Obteniendo todos los técnicos - Total: " + tecnicos.size());

        return tecnicos;
    }

    /**
     * Busca un técnico por su ID único
     * @param id ID del técnico a buscar
     * @return Optional con el técnico si existe, o vacío si no se encuentra
     * @throws IllegalArgumentException si el id es nulo
     */
    @Override
    public Optional<Tecnico> findById(Long id) {
        // Validación básica del parámetro
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        System.out.println("Buscando técnico con ID: " + id);

        // Delegar la búsqueda al repositorio
        return tecnicoRepository.findById(id);
    }

    /**
     * Guarda un nuevo técnico en el sistema a partir de un DTO
     * Convierte el DTO en una entidad Tecnico y la persiste
     *
     * @param tecnicoDTO Objeto de transferencia de datos con la información del técnico
     * @return El técnico guardado con su ID generado automáticamente
     * @throws IllegalArgumentException si el tecnicoDTO es nulo
     */
    @Override
    public Tecnico save(TecnicoDTO tecnicoDTO) {
        // Validar que el DTO no sea nulo
        if (tecnicoDTO == null) {
            throw new IllegalArgumentException("El DTO del técnico no puede ser nulo");
        }

        // Validar campos obligatorios del DTO
        if (tecnicoDTO.getNombre() == null || tecnicoDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del técnico es obligatorio");
        }

        if (tecnicoDTO.getEspecialidad() == null || tecnicoDTO.getEspecialidad().trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad del técnico es obligatoria");
        }

        // Crear nueva entidad Tecnico a partir del DTO
        Tecnico tecnico = new Tecnico();
        tecnico.setId(idCounter.getAndIncrement()); // Generar ID automático
        tecnico.setNombre(tecnicoDTO.getNombre().trim());
        tecnico.setEspecialidad(tecnicoDTO.getEspecialidad().trim());

        // Aplicar formato estándar a la especialidad (primera letra mayúscula)
        tecnico.setEspecialidad(capitalizeFirstLetter(tecnico.getEspecialidad()));

        // Guardar en el repositorio
        Tecnico tecnicoGuardado = tecnicoRepository.save(tecnico);

        System.out.println("Técnico creado exitosamente - ID: " + tecnicoGuardado.getId() +
                ", Nombre: " + tecnicoGuardado.getNombre());

        return tecnicoGuardado;
    }

    /**
     * Actualiza un técnico existente con la información proporcionada en el DTO
     * Busca el técnico por ID y actualiza sus campos
     *
     * @param id ID del técnico a actualizar
     * @param tecnicoDTO DTO con los nuevos datos del técnico
     * @return El técnico actualizado
     * @throws RuntimeException si no se encuentra el técnico con el ID especificado
     */
    @Override
    public Tecnico update(Long id, TecnicoDTO tecnicoDTO) {
        // Validar parámetros
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        if (tecnicoDTO == null) {
            throw new IllegalArgumentException("El DTO del técnico no puede ser nulo");
        }

        // Buscar el técnico existente
        Optional<Tecnico> tecnicoExistente = tecnicoRepository.findById(id);

        if (tecnicoExistente.isPresent()) {
            Tecnico tecnico = tecnicoExistente.get();

            // Actualizar solo los campos permitidos (no actualizamos el ID)
            if (tecnicoDTO.getNombre() != null && !tecnicoDTO.getNombre().trim().isEmpty()) {
                tecnico.setNombre(tecnicoDTO.getNombre().trim());
            }

            if (tecnicoDTO.getEspecialidad() != null && !tecnicoDTO.getEspecialidad().trim().isEmpty()) {
                tecnico.setEspecialidad(capitalizeFirstLetter(tecnicoDTO.getEspecialidad().trim()));
            }

            // Guardar los cambios en el repositorio
            Tecnico tecnicoActualizado = tecnicoRepository.update(tecnico);

            System.out.println("Técnico actualizado exitosamente - ID: " + id +
                    ", Nuevo nombre: " + tecnicoActualizado.getNombre() +
                    ", Nueva especialidad: " + tecnicoActualizado.getEspecialidad());

            return tecnicoActualizado;

        } else {
            // Lanzar excepción si el técnico no existe
            throw new RuntimeException("Técnico no encontrado con ID: " + id);
        }
    }

    /**
     * Elimina un técnico del sistema por su ID
     * @param id ID del técnico a eliminar
     */
    @Override
    public void deleteById(Long id) {
        // Validar parámetro
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        // Verificar si el técnico existe antes de eliminar
        if (tecnicoRepository.existsById(id)) {
            // Podríamos agregar lógica de negocio aquí (ej: verificar si el técnico tiene solicitudes activas)

            tecnicoRepository.deleteById(id);
            System.out.println("Técnico con ID " + id + " eliminado correctamente");
        } else {
            System.out.println("No se pudo eliminar: Técnico con ID " + id + " no encontrado");
            // Podríamos lanzar una excepción aquí si es requerido
        }
    }

    /**
     * Busca técnicos por su especialidad (búsqueda exacta case-insensitive)
     * @param especialidad Especialidad de los técnicos a buscar
     * @return Lista de técnicos que tienen la especialidad especificada
     * @throws IllegalArgumentException si la especialidad es nula o vacía
     */
    @Override
    public List<Tecnico> findByEspecialidad(String especialidad) {
        // Validar parámetro
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía");
        }

        System.out.println("Buscando técnicos con especialidad: " + especialidad);

        // Delegar la búsqueda al repositorio
        return tecnicoRepository.findByEspecialidad(especialidad);
    }

    /**
     * Busca técnicos por su nombre (búsqueda parcial case-insensitive)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de técnicos que coinciden con el nombre
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    @Override
    public List<Tecnico> findByNombreContaining(String nombre) {
        // Validar parámetro
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        System.out.println("Buscando técnicos con nombre que contenga: " + nombre);

        // Delegar la búsqueda al repositorio
        return tecnicoRepository.findByNombreContaining(nombre);
    }

    /**
     * Verifica si existe un técnico con el ID especificado
     * @param id ID del técnico a verificar
     * @return true si existe, false si no existe
     */
    @Override
    public boolean existsById(Long id) {
        // Validar parámetro
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        return tecnicoRepository.existsById(id);
    }

    /**
     * Obtiene el número total de técnicos registrados en el sistema
     * @return Cantidad total de técnicos
     */
    @Override
    public long count() {
        long total = tecnicoRepository.count();
        System.out.println("Total de técnicos en el sistema: " + total);
        return total;
    }

    /**
     * Obtiene la lista de todas las especialidades únicas disponibles en el sistema
     * @return Lista de especialidades sin duplicados
     */
    @Override
    public List<String> findAllEspecialidades() {
        List<String> especialidades = tecnicoRepository.findAllEspecialidades();
        System.out.println("Especialidades disponibles: " + especialidades);
        return especialidades;
    }

    /**
     * Método de utilidad para capitalizar la primera letra de un string
     * Convierte "redes" en "Redes", "BASE DE DATOS" en "Base de datos", etc.
     *
     * @param text Texto a capitalizar
     * @return Texto con la primera letra en mayúscula y el resto en minúscula
     */
    private String capitalizeFirstLetter(String text) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }

        text = text.toLowerCase().trim();

        // Capitalizar cada palabra (para casos como "base de datos")
        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1));
            }
        }

        return result.toString();
    }

    /**
     * Método adicional para obtener estadísticas de los técnicos
     * @return String con estadísticas detalladas
     */
    public String getEstadisticas() {
        long total = count();
        List<String> especialidades = findAllEspecialidades();

        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS DEL SERVICIO DE TÉCNICOS ===\n");
        stats.append("Total de técnicos registrados: ").append(total).append("\n");
        stats.append("Número de especialidades: ").append(especialidades.size()).append("\n");

        // Estadísticas por especialidad
        for (String especialidad : especialidades) {
            List<Tecnico> tecnicosEspecialidad = findByEspecialidad(especialidad);
            stats.append("  - ").append(especialidad).append(": ")
                    .append(tecnicosEspecialidad.size()).append(" técnico(s)\n");
        }

        return stats.toString();
    }
}
