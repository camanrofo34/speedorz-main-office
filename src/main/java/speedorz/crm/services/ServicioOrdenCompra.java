package speedorz.crm.services;

import speedorz.crm.domain.dto.request.OrdenCompraDTO;
import speedorz.crm.domain.entities.OrdenCompra;

import java.util.List;

/**
 * Servicio para la gestión de órdenes de compra en el sistema CRM.
 * Proporciona operaciones para crear, eliminar y consultar órdenes de compra.
 */
public interface ServicioOrdenCompra {

    /**
     * Registra una nueva orden de compra en el sistema.
     *
     * @param ordenCompra DTO que contiene la información de la orden de compra a registrar.
     * @return OrdenCompra creada con su identificador asignado.
     */
    OrdenCompra plantarOrdenCompra(OrdenCompraDTO ordenCompra);

    /**
     * Elimina una orden de compra del sistema por su ID.
     *
     * @param id Identificador único de la orden de compra a eliminar.
     */
    void eliminarOrdenCompra(Long id);

    /**
     * Busca una orden de compra por su identificador único.
     *
     * @param id ID de la orden de compra a buscar.
     * @return OrdenCompra encontrada o {@code null} si no existe.
     */
    OrdenCompra buscarOrdenCompraPorId(Long id);

    /**
     * Obtiene una lista de todas las órdenes de compra registradas en el sistema.
     *
     * @return Lista de órdenes de compra disponibles.
     */
    List<OrdenCompra> listarOrdenCompras();
}

