package speedorz.crm.services;

import speedorz.crm.domain.entities.Factura;
import java.io.IOException;
import java.util.List;

/**
 * Servicio para la gestión de facturas en el sistema CRM.
 * Proporciona operaciones para la generación, eliminación y consulta de facturas.
 */
public interface ServicioFactura {

    /**
     * Genera una nueva factura en el sistema.
     *
     * @param factura Objeto con los datos de la factura a registrar.
     * @return Factura generada con su ID asignado.
     */
    Factura generarFactura(Factura factura);

    /**
     * Elimina una factura del sistema por su ID.
     *
     * @param id Identificador único de la factura a eliminar.
     */
    void eliminarFactura(Long id);

    /**
     * Busca una factura por su identificador único.
     *
     * @param id ID de la factura a buscar.
     * @return Factura encontrada o {@code null} si no existe.
     */
    Factura buscarFacturaPorId(Long id);

    /**
     * Obtiene una lista de todas las facturas registradas en el sistema.
     *
     * @return Lista de facturas disponibles.
     */
    List<Factura> listarFacturas();

    //Generar facturapdf
    byte[] generarFacturaPDF(Long facturaId) throws IOException;
}
