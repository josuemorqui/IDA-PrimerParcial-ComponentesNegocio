package com.example.soporte_tecnico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Objeto de Transferencia de Datos (DTO) para la entidad Tecnico
 *
 * DTO Pattern: Separa la capa de presentación (API) de la capa de dominio (Model)
 *
 * Ventajas de usar DTOs:
 * ✅ Evita exponer la entidad completa (mayor seguridad)
 * ✅ Permite personalizar los datos que se envían/reciben
 * ✅ Facilita la evolución independiente de la API y el modelo
 * ✅ Mejor control sobre las validaciones de entrada
 */
public class TecnicoDTO {

    /**
     * Nombre completo del técnico
     * Ejemplo: "Carlos López"
     */
    @NotBlank(message = "El nombre del técnico es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    /**
     * Especialidad del técnico
     * Ejemplo: "Redes"
     */
    @NotBlank(message = "La especialidad del técnico es obligatoria")
    @Size(min = 2, max = 50, message = "La especialidad debe tener entre 2 y 50 caracteres")
    private String especialidad;

    /**
     * Constructor vacío (OBLIGATORIO para frameworks como Spring y Jackson)
     * Permite la deserialización de JSON a objetos Java
     */
    public TecnicoDTO() {
        // Constructor vacío requerido para la deserialización JSON
    }

    /**
     * Constructor con parámetros para facilitar la creación de instancias
     * @param nombre Nombre completo del técnico
     * @param especialidad Especialidad del técnico
     */
    public TecnicoDTO(String nombre, String especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    // =====================
    // GETTERS Y SETTERS
    // =====================

    /**
     * Obtiene el nombre del técnico
     * @return Nombre completo del técnico
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del técnico
     * @param nombre Nombre completo del técnico
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la especialidad del técnico
     * @return Especialidad del técnico
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece la especialidad del técnico
     * @param especialidad Especialidad del técnico
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // =====================
    // MÉTODOS SOBREESCRITOS
    // =====================

    /**
     * Representación en String del objeto TecnicoDTO
     * Útil para logging y debugging
     *
     * @return String que representa el objeto TecnicoDTO
     */
    @Override
    public String toString() {
        return "TecnicoDTO{" +
                "nombre='" + nombre + '\'' +
                ", especialidad='" + especialidad + '\'' +
                '}';
    }

    /**
     * Compara este TecnicoDTO con otro objeto para determinar igualdad
     * Basado en los valores de nombre y especialidad
     *
     * @param obj Objeto a comparar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        TecnicoDTO that = (TecnicoDTO) obj;

        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        return especialidad != null ? especialidad.equals(that.especialidad) : that.especialidad == null;
    }

    /**
     * Genera un código hash para el objeto TecnicoDTO
     * Basado en los valores de nombre y especialidad
     *
     * @return Código hash del objeto
     */
    @Override
    public int hashCode() {
        int result = nombre != null ? nombre.hashCode() : 0;
        result = 31 * result + (especialidad != null ? especialidad.hashCode() : 0);
        return result;
    }

    // =====================
    // MÉTODOS DE UTILIDAD
    // =====================

    /**
     * Valida si el DTO contiene datos básicos válidos
     * Más allá de las validaciones de anotaciones
     *
     * @return true si el DTO es válido, false en caso contrario
     */
    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty() &&
                especialidad != null && !especialidad.trim().isEmpty();
    }

    /**
     * Limpia los espacios en blanco al inicio y final de los campos
     * Útil para normalizar los datos antes de procesarlos
     */
    public void trimFields() {
        if (nombre != null) {
            nombre = nombre.trim();
        }
        if (especialidad != null) {
            especialidad = especialidad.trim();
        }
    }

    /**
     * Crea una copia de este objeto TecnicoDTO
     *
     * @return Nueva instancia con los mismos valores
     */
    public TecnicoDTO copy() {
        return new TecnicoDTO(
                this.nombre != null ? new String(this.nombre) : null,
                this.especialidad != null ? new String(this.especialidad) : null
        );
    }
}