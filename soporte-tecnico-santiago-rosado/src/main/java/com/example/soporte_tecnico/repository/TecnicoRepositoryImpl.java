package com.example.soporte_tecnico.repository;

import com.example.soporte_tecnico.model.Tecnico;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementación concreta del repositorio de técnicos usando almacenamiento en memoria
 *
 * @Repository Indica que esta clase es un componente de repositorio de Spring
 *             Permite la inyección de dependencias y el manejo automático de excepciones
 *
 * Esta implementación simula una base de datos usando una ArrayList en memoria
 * Es adecuada para desarrollo, pruebas y demostraciones
 */
@Repository
public class TecnicoRepositoryImpl implements TecnicoRepository {

    // Simulación de tabla de técnicos en memoria
    private final List<Tecnico> tecnicos = new ArrayList<>();

    // Generador de IDs automáticos (simula AUTO_INCREMENT de base de datos)
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Constructor que inicializa datos de ejemplo al crear el repositorio
     */
    public TecnicoRepositoryImpl() {
        // Inicializar con algunos técnicos de ejemplo
        initializeSampleData();
    }

    /**
     * Obtiene todos los técnicos registrados en el sistema
     * @return Lista inmutable de todos los técnicos
     */
    @Override
    public List<Tecnico> findAll() {
        // Retornamos una copia para evitar modificaciones externas a la lista interna
        return new ArrayList<>(tecnicos);
    }

    /**
     * Busca un técnico por su ID único
     * @param id ID del técnico a buscar
     * @return Optional con el técnico si existe, o vacío si no se encuentra
     * @throws IllegalArgumentException si el id es nulo
     */
    @Override
    public Optional<Tecnico> findById(Long id) {
        // Validar parámetro de entrada
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        // Buscar técnico por ID usando Stream API
        return tecnicos.stream()
                .filter(tecnico -> id.equals(tecnico.getId()))
                .findFirst();
    }

    /**
     * Busca técnicos por su nombre (búsqueda parcial case-insensitive)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de técnicos que coinciden con el nombre
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    @Override
    public List<Tecnico> findByNombreContaining(String nombre) {
        // Validar parámetro de entrada
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        String nombreLower = nombre.toLowerCase();

        // Filtrar técnicos cuyo nombre contenga el texto buscado (case-insensitive)
        return tecnicos.stream()
                .filter(tecnico -> tecnico.getNombre().toLowerCase().contains(nombreLower))
                .collect(Collectors.toList());
    }

    /**
     * Busca técnicos por su especialidad (búsqueda exacta case-insensitive)
     * @param especialidad Especialidad de los técnicos a buscar
     * @return Lista de técnicos que tienen la especialidad especificada
     * @throws IllegalArgumentException si la especialidad es nulo o vacía
     */
    @Override
    public List<Tecnico> findByEspecialidad(String especialidad) {
        // Validar parámetro de entrada
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía");
        }

        String especialidadLower = especialidad.toLowerCase();

        // Filtrar técnicos por especialidad (case-insensitive)
        return tecnicos.stream()
                .filter(tecnico -> especialidadLower.equals(tecnico.getEspecialidad().toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Guarda un nuevo técnico en el repositorio
     * Si el técnico no tiene ID, se le asignará uno automáticamente
     * @param tecnico Técnico a guardar
     * @return El técnico guardado con su ID asignado
     * @throws IllegalArgumentException si el técnico es nulo
     */
    @Override
    public Tecnico save(Tecnico tecnico) {
        // Validar parámetro de entrada
        if (tecnico == null) {
            throw new IllegalArgumentException("El técnico no puede ser nulo");
        }

        // Asignar ID automático si no tiene
        if (tecnico.getId() == null) {
            tecnico.setId(idGenerator.getAndIncrement());
        } else {
            // Verificar que el ID no esté duplicado
            if (existsById(tecnico.getId())) {
                throw new IllegalArgumentException("Ya existe un técnico con ID: " + tecnico.getId());
            }
        }

        // Agregar técnico a la lista
        tecnicos.add(tecnico);

        System.out.println("Técnico guardado - ID: " + tecnico.getId() +
                ", Nombre: " + tecnico.getNombre() +
                ", Especialidad: " + tecnico.getEspecialidad());

        return tecnico;
    }

    /**
     * Actualiza un técnico existente en el repositorio
     * @param tecnico Técnico con los datos actualizados
     * @return El técnico actualizado
     * @throws RuntimeException si el técnico no existe en el repositorio
     */
    @Override
    public Tecnico update(Tecnico tecnico) {
        // Validar parámetro de entrada
        if (tecnico == null) {
            throw new IllegalArgumentException("El técnico no puede ser nulo");
        }

        // Buscar el técnico existente
        Optional<Tecnico> tecnicoExistente = findById(tecnico.getId());

        if (tecnicoExistente.isPresent()) {
            // Eliminar el técnico antiguo
            deleteById(tecnico.getId());

            // Agregar el técnico actualizado
            tecnicos.add(tecnico);

            System.out.println("Técnico actualizado - ID: " + tecnico.getId() +
                    ", Nombre: " + tecnico.getNombre() +
                    ", Especialidad: " + tecnico.getEspecialidad());

            return tecnico;
        } else {
            throw new RuntimeException("No se puede actualizar: Técnico no encontrado con ID: " + tecnico.getId());
        }
    }

    /**
     * Elimina un técnico del repositorio por su ID
     * @param id ID del técnico a eliminar
     * @throws IllegalArgumentException si el id es nulo
     */
    @Override
    public void deleteById(Long id) {
        // Validar parámetro de entrada
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        // Eliminar técnico si existe
        boolean removed = tecnicos.removeIf(tecnico -> id.equals(tecnico.getId()));

        if (removed) {
            System.out.println("Técnico eliminado - ID: " + id);
        } else {
            System.out.println("No se encontró técnico con ID: " + id + " para eliminar");
        }
    }

    /**
     * Verifica si existe un técnico con el ID especificado
     * @param id ID del técnico a verificar
     * @return true si existe un técnico con ese ID, false en caso contrario
     * @throws IllegalArgumentException si el id es nulo
     */
    @Override
    public boolean existsById(Long id) {
        // Validar parámetro de entrada
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        return tecnicos.stream().anyMatch(tecnico -> id.equals(tecnico.getId()));
    }

    /**
     * Obtiene el número total de técnicos registrados en el sistema
     * @return Cantidad total de técnicos
     */
    @Override
    public long count() {
        return tecnicos.size();
    }

    /**
     * Obtiene la lista de todas las especialidades únicas disponibles en el sistema
     * @return Lista de especialidades sin duplicados
     */
    @Override
    public List<String> findAllEspecialidades() {
        return tecnicos.stream()
                .map(Tecnico::getEspecialidad)
                .distinct() // Eliminar duplicados
                .collect(Collectors.toList());
    }

    /**
     * Método de utilidad para inicializar datos de ejemplo
     * Se ejecuta automáticamente en el constructor
     */
    private void initializeSampleData() {
        if (tecnicos.isEmpty()) {
            // Crear técnicos de ejemplo con diferentes especialidades
            save(new Tecnico(null, "Carlos López", "Redes"));
            save(new Tecnico(null, "Ana Martínez", "Software"));
            save(new Tecnico(null, "Pedro García", "Hardware"));
            save(new Tecnico(null, "Luisa Fernández", "Base de Datos"));
            save(new Tecnico(null, "Miguel Rodríguez", "Redes")); // Especialidad duplicada
            save(new Tecnico(null, "Elena Castro", "Seguridad"));

            System.out.println("Datos de ejemplo de técnicos inicializados - Total: " + count() + " técnicos");
            System.out.println("Especialidades disponibles: " + findAllEspecialidades());
        }
    }

    /**
     * Método de utilidad para limpiar todos los técnicos (útil para testing)
     * ATENCIÓN: Este método elimina todos los datos, usar con cuidado
     */
    public void deleteAll() {
        tecnicos.clear();
        idGenerator.set(1); // Reiniciar el generador de IDs
        System.out.println("Todos los técnicos han sido eliminados");
    }

    /**
     * Método de utilidad para obtener estadísticas de los técnicos
     * @return String con estadísticas del repositorio
     */
    public String getEstadisticas() {
        long total = count();
        List<String> especialidades = findAllEspecialidades();

        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS DE TÉCNICOS ===\n");
        stats.append("Total de técnicos: ").append(total).append("\n");
        stats.append("Especialidades: ").append(especialidades.size()).append("\n");
        stats.append("Lista de especialidades: ").append(String.join(", ", especialidades)).append("\n");

        // Contar técnicos por especialidad
        for (String especialidad : especialidades) {
            long count = findByEspecialidad(especialidad).size();
            stats.append("  - ").append(especialidad).append(": ").append(count).append(" técnico(s)\n");
        }

        return stats.toString();
    }
}
