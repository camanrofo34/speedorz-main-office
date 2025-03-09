package speedorz.crm.services;

import speedorz.crm.domain.entities.OrdenVehiculo;

import java.util.List;

/**
 * Servicio para la gestión de las órdenes de vehículos dentro del CRM.
 * Proporciona operaciones para crear, eliminar y consultar órdenes de vehículos.
 */
public interface ServicioOrdenVehiculo {

    /**
     * Registra una nueva orden de vehículo en el sistema.
     *
     * @param ordenVehiculo Entidad que representa la orden de vehículo a registrar.
     * @return La OrdenVehiculo creada con su identificador asignado.
     */
    OrdenVehiculo crearOrdenVehiculo(OrdenVehiculo ordenVehiculo);

    /**
     * Elimina una orden de vehículo del sistema por su ID.
     *
     * @param id Identificador único de la orden de vehículo a eliminar.
     */
    void eliminarOrdenVehiculo(Long id);

    /**
     * Busca una orden de vehículo por su identificador único.
     *
     * @param id ID de la orden de vehículo a buscar.
     * @return OrdenVehiculo encontrada o {@code null} si no existe.
     */
    OrdenVehiculo buscarOrdenVehiculoPorId(Long id);

    /**
     * Obtiene una lista de todas las órdenes de vehículos registradas en el sistema.
     *
     * @return Lista de órdenes de vehículos disponibles.
     */
    List<OrdenVehiculo> listarOrdenVehiculos();
}

