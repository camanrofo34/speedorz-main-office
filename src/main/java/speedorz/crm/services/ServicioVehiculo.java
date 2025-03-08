package speedorz.crm.services;

import speedorz.crm.domain.entities.Vehiculo;
import java.util.List;

/**
 * Servicio para la gestión de vehículos dentro del CRM.
 * Proporciona operaciones para la creación, actualización, eliminación y consulta de vehículos.
 */
public interface ServicioVehiculo {

    /**
     * Registra un nuevo vehículo en el sistema.
     *
     * @param vehiculo Entidad que representa el vehículo a registrar.
     * @return El Vehículo creado con su identificador asignado.
     */
    Vehiculo crearVehiculo(Vehiculo vehiculo);

    /**
     * Actualiza la información de un vehículo existente.
     *
     * @param vehiculo Entidad que contiene la información actualizada del vehículo.
     */
    void actualizarVehiculo(Vehiculo vehiculo);

    /**
     * Elimina un vehículo del sistema por su ID.
     *
     * @param id Identificador único del vehículo a eliminar.
     */
    void eliminarVehiculo(Long id);

    /**
     * Obtiene una lista de todos los vehículos registrados en el sistema.
     *
     * @return Lista de vehículos disponibles.
     */
    List<Vehiculo> listarVehiculos();

    /**
     * Busca un vehículo por su identificador único.
     *
     * @param id ID del vehículo a buscar.
     * @return Vehículo encontrado o {@code null} si no existe.
     */
    Vehiculo buscarVehiculoPorId(Long id);

    /**
     * Busca vehículos cuyo nombre coincida parcial o totalmente con el criterio de búsqueda.
     *
     * @param nombre Nombre del vehículo o parte del nombre a buscar.
     * @return Lista de vehículos que coincidan con el criterio de búsqueda.
     */
    List<Vehiculo> buscarVehiculosPorNombre(String nombre);
}
